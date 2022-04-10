package cn.tragoedia.bbs.service;

import cn.tragoedia.bbs.entity.User;

public interface UserService {
    User findUserById(int id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    int updateStatusById(int id, int status);

    int updateHeaderById(int id, String headerUrl);

    int updatePasswordById(int id, String password);
}
