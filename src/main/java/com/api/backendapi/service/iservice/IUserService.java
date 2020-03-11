package com.api.backendapi.service.iservice;

import com.api.backendapi.entity.User;

public interface IUserService {
    User getUserById(Long id);

    boolean saveUser(User user);

    User checkLogin(String username, String password);
}
