package cn.tragoedia.bbs.service.impl;

import cn.tragoedia.bbs.entity.LoginTicket;
import cn.tragoedia.bbs.entity.User;
import cn.tragoedia.bbs.repository.LoginTicketRepository;
import cn.tragoedia.bbs.repository.UserRepository;
import cn.tragoedia.bbs.service.UserService;
import cn.tragoedia.bbs.utils.CommonUtil;
import cn.tragoedia.bbs.utils.Constant;
import cn.tragoedia.bbs.utils.MailClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService, Constant {
    @Resource
    private UserRepository userRepository;
    @Resource
    private MailClientUtil mailClientUtil;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private LoginTicketRepository loginTicketRepository;

    @Value("${bbs.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public User findUserById(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User insertUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public int updateStatusById(int id, int status) {
        return userRepository.updateStatusById(id, status);
    }

    @Override
    public int updateHeaderById(int id, String headerUrl) {
        return userRepository.updateHeaderById(id, headerUrl);
    }

    @Override
    public int updatePasswordById(int id, String password) {
        return userRepository.updatePasswordById(id, password);
    }

    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> registerMap = new HashMap<>();
        // 空值处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            registerMap.put("usernameMsg", "账号不能为空");
            return registerMap;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            registerMap.put("passwordMsg", "密码不能为空");
            return registerMap;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            registerMap.put("emailMsg", "邮箱不能为空");
            return registerMap;
        }
        // 验证账号
        User userByUsername = userRepository.findUserByUsername(user.getUsername());
        if (userByUsername != null) {
            registerMap.put("usernameMsg", "该账号已存在");
            return registerMap;
        }
        User userByEmail = userRepository.findUserByEmail(user.getEmail());
        if (userByEmail != null) {
            registerMap.put("emailMsg", "该邮箱已被注册");
            return registerMap;
        }
        // 注册
        user.setSalt(CommonUtil.generateUUID().substring(0, 5));
        user.setPassword(CommonUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommonUtil.generateUUID());
        user.setHeaderUrl("");
        user.setCreateTime(new Date());
        userRepository.save(user);
        // 激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClientUtil.sendMail(user.getEmail(), "账号激活", content);

        return registerMap;
    }

    @Override
    public int activation(int id, String code) {
        User user = userRepository.findUserById(id);
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userRepository.updateStatusById(id, 1);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    @Override
    public Map<String, Object> login(String username, String password, int expired) {
        Map<String, Object> map = new HashMap<>();
        // 验证空值
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        // 查询账号
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            map.put("usernameMsg", "账号不存在");
            return map;
        }
        // 验证状态
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "账号未激活");
            return map;
        }
        // 验证密码
        if (!user.getPassword().equals(CommonUtil.md5(password + user.getSalt()))) {
            map.put("passwordMsg", "密码错误");
            return map;
        }
        // 生成凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommonUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expired * 1000));
        loginTicketRepository.save(loginTicket);

        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    @Override
    public void logout(String ticket) {
        loginTicketRepository.updateStatusByTicket(ticket, 1);
    }
}
