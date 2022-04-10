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
}
