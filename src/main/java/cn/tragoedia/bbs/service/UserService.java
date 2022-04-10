package cn.tragoedia.bbs.service;

import cn.tragoedia.bbs.entity.User;

public interface UserService {
    User findUserByUsername(String username);
}
