package cn.tragoedia.bbs.controller;

import cn.tragoedia.bbs.entity.User;
import cn.tragoedia.bbs.service.UserService;
import cn.tragoedia.bbs.utils.Constant;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class LoginController implements Constant {
    @Resource
    private UserService userService;

    @Resource
    private Producer kaptchaProducer;

    @GetMapping("/register")
    public String getRegisterPage() {
        return "site/register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "site/login";
    }

    @GetMapping("/forget")
    public String getForgetPage() {
        return "site/forget";
    }

    @PostMapping("/register")
    public String register(Model model, User user) {
        Map<String, Object> registerMap = userService.register(user);
        if (registerMap == null || registerMap.isEmpty()) {
            model.addAttribute("msg", "注册成功，已向您的注册邮箱发送了一封激活邮件，请尽快激活。");
            model.addAttribute("target", "/index");
            return "site/operate-result";
        } else {
            model.addAttribute("usernameMsg", registerMap.get("usernameMsg"));
            model.addAttribute("passwordMsg", registerMap.get("passwordMsg"));
            model.addAttribute("emailMsg", registerMap.get("emailMsg"));
            return "site/register";
        }
    }

    @GetMapping("/activation/{id}/{code}")
    public String activation(Model model, @PathVariable int id, @PathVariable String code) {
        int result = userService.activation(id, code);
        if (result == ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功，账号已可正常使用");
            model.addAttribute("target", "/login");
        } else if (result == ACTIVATION_REPEAT) {
            model.addAttribute("msg", "该账号已经激活！");
            model.addAttribute("target", "/index");
        } else {
            model.addAttribute("msg", "激活失败！");
            model.addAttribute("target", "/index");
        }
        return "site/operate-result";
    }

    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response, HttpSession session) {
        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);
        // 存入session
        session.setAttribute("kaptcha", text);
        // 输出浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            log.error("响应验证码失败" + e.getMessage());
        }
    }

    @PostMapping("/login")
    public String login(Model model, HttpSession session, HttpServletResponse response, String username, String password, String verifyCode, boolean rememberMe) {
        String kaptcha = (String) session.getAttribute("kaptcha");
        // 验证码检测
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(verifyCode) || !kaptcha.equalsIgnoreCase(verifyCode)) {
            model.addAttribute("verifyCodeMsg", "验证码错误");
            return "site/login";
        }
        // 账号检测
        int expiredSeconds = rememberMe ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username, password, expiredSeconds);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/index";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "site/login";
        }
    }

    // 获取验证码
    @GetMapping("/forget/code")
    @ResponseBody
    public Map<String, Object> getForgetCode(String email, HttpSession session) {
        Map<String, Object> forgetCode = userService.getForgetCode(email);
        Map<String, Object> map = new HashMap<>();
        Object code = forgetCode.get("code");

        // 保存验证码
        session.setAttribute("verifyCode", code);
        map.put("code", 0);
        return map;
    }

    // 重置密码
    @PostMapping("/forget/password")
    public String resetPassword(String email, String verifyCode, String password, Model model, HttpSession session) {
        String code = (String) session.getAttribute("verifyCode");
        if (StringUtils.isBlank(verifyCode) || StringUtils.isBlank(code) || !code.equalsIgnoreCase(verifyCode)) {
            model.addAttribute("codeMsg", "验证码错误!");
            return "site/forget";
        }

        Map<String, Object> map = userService.resetPassword(email, password);
        if (map.containsKey("user")) {
            return "redirect:/login";
        } else {
            model.addAttribute("emailMsg", map.get("emailMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "site/forget";
        }
    }

    @GetMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/login";
    }

}
