package com.api.backendapi.controller;

import com.api.backendapi.entity.User;
import com.api.backendapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    IUserService userService;

    @RequestMapping(value = "/api/v1/user/createAdmin", method = RequestMethod.GET)
    @ResponseBody
    private ResponseEntity<Object> createAdmin() {
        User user = new User();
        user.setName("Admin");
        user.setActive(true);
        user.setBarcode("testBarCode");
        user.setRole("admin");
        user.setUsername("admin");
        user.setPassword("password");
        if (userService.saveUser(user) == false) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/v1/user/login", method = RequestMethod.POST)
    @ResponseBody
    private ResponseEntity<Object> checkLogin(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User u = userService.checkLogin(username, password);
        if (u == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

//    @RequestMapping(value = "/api/v1/user/test", method = RequestMethod.GET)
//    @ResponseBody
//    private ResponseEntity<Object> test() {
//        User user = new User();
//        user.setId(new Long(1));
//        user.setName("Duong");
////        user.setAge(50);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
}
