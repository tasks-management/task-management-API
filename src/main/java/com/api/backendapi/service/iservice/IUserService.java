package com.api.backendapi.service.iservice;

import com.api.backendapi.entity.User;

import java.util.List;

public interface IUserService {

    User getUserById(Long id);

    User saveUser(User user);

    User checkLogin(String username, String password);

    List<User> getAllManagerUser();

    List<User> getAllUserInTeam(Long userId);

    List<User> getAllAdminUsers();
}
