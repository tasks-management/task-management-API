package com.api.backendapi.service;

import com.api.backendapi.entity.User;
import com.api.backendapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public boolean saveUser(User user) {
        user = userRepository.save(user);
        if (user != null) {
            return true;
        }
        return false;
    }
}
