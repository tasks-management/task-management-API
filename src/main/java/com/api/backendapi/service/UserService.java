package com.api.backendapi.service;

import com.api.backendapi.entity.User;
import com.api.backendapi.repository.UserRepository;
import com.api.backendapi.service.iservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User checkLogin(String username, String password) {
        return userRepository.checkLogin(username, password);
    }

    @Override
    public List<User> getAllManagerUser() {
        return userRepository.getAllManagerUser();
    }

    @Override
    public List<User> getAllUserInTeam(Long userId) {
        return userRepository.getAllUserInTeam(userId);
    }
}
