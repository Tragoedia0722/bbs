package cn.tragoedia.bbs.controller;

import cn.tragoedia.bbs.entity.User;
import cn.tragoedia.bbs.service.UserService;
import cn.tragoedia.bbs.utils.Constant;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
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
        return "/site/register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/site/login";
    }

    @PostMapping("/register")
    public String register(Model model, User user) {
        Map<String, Object> registerMap = userService.register(user);
        if (registerMap == null || registerMap.isEmpty()) {
            model.addAttribute("msg", "注册成功，已向您的注册邮箱发送了一封激活邮件，请尽快激活。");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
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
        return "/site/operate-result";
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

}
