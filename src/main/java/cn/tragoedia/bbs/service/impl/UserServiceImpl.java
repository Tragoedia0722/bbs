package cn.tragoedia.bbs.service.impl;

import cn.tragoedia.bbs.entity.User;
import cn.tragoedia.bbs.repository.UserRepository;
import cn.tragoedia.bbs.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
