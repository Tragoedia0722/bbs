package cn.tragoedia.bbs.service;

import cn.tragoedia.bbs.entity.LoginTicket;
import cn.tragoedia.bbs.entity.User;

import java.util.Map;

public interface UserService {
    User findUserById(int id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User insertUser(User user);

    int updateStatusById(int id, int status);

    int updateHeaderById(int id, String headerUrl);

    int updatePasswordById(int id, String password);

    Map<String, Object> register(User user);

    int activation(int id, String code);

    Map<String, Object> login(String username, String password, int expired);

    void logout(String ticket);

    LoginTicket findLoginTicket(String value);
}
