package cn.tragoedia.bbs.controller;

import cn.tragoedia.bbs.annotation.LoginRequired;
import cn.tragoedia.bbs.entity.User;
import cn.tragoedia.bbs.service.UserService;
import cn.tragoedia.bbs.utils.CommonUtil;
import cn.tragoedia.bbs.utils.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Value("${bbs.path.upload}")
    private String uploadPath;
    @Value("${bbs.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Resource
    private UserService userService;
    @Resource
    private HostHolder hostHolder;

    @LoginRequired
    @GetMapping("/setting")
    public String getSettingPage() {
        return "site/setting";
    }

    @LoginRequired
    @PostMapping("/upload")
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "尚未选择图片");
            return "site/setting";
        }
        // 获取后缀
        String originalFilename = headerImage.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件格式错误");
        }
        // 生成文件名
        String fileName = CommonUtil.generateUUID() + suffix;
        File dest = new File(uploadPath + "/" + fileName);
        try {
            BufferedImage read = ImageIO.read(headerImage.getInputStream());
            if (read == null) {
                throw new RuntimeException("上传文件非图片格式");
            }
            headerImage.transferTo(dest);
        } catch (IOException e) {
            log.error("上传文件失败" + e.getMessage());
            throw new RuntimeException("上传文件失败", e);
        }
        // 更新地址
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

    @GetMapping("/header/{fileName}")
    public void getHeader(@PathVariable String fileName, HttpServletResponse response) {
        // 存放路径
        fileName = uploadPath + "/" + fileName;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        response.setContentType("image/" + suffix);
        try (
                ServletOutputStream outputStream = response.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(fileName)
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, b);
            }
        } catch (IOException e) {
            log.error("头像读取失败" + e.getMessage());
        }
    }

    @LoginRequired
    @PostMapping("/updatePassword")
    public String updatePassword(String oldPassword, String newPassword, Model model) {
        User user = hostHolder.getUser();
        Map<String, Object> map = userService.updatePassword(user.getId(), oldPassword, newPassword);
        if (map.isEmpty() || map == null) {
            return "redirect:/logout";
        } else {
            model.addAttribute("oldPasswordMsg", map.get("oldPasswordMsg"));
            model.addAttribute("newPasswordMsg", map.get("newPasswordMsg"));
            return "/site/setting";
        }
    }
}
