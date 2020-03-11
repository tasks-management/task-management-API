package com.api.backendapi.controller;

import com.api.backendapi.entity.Task;
import com.api.backendapi.service.iservice.ITaskService;
import com.google.gson.JsonObject;
import com.api.backendapi.entity.User;
import com.api.backendapi.service.iservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    ITaskService taskService;

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/api/v1/user/createAdmin", method = RequestMethod.GET)
    @ResponseBody
    private ResponseEntity<Object> createAdmin() {
        User user = new User();
        user.setName("Admin");
        user.setActive(true);
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

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/create", method = RequestMethod.POST)
    public ResponseEntity<Object> createNewUser(@RequestBody User user) {
        Boolean result = userService.saveUser(user);
        JsonObject jsonObject = new JsonObject();
        if (!result) {
            jsonObject.addProperty("message", "Create failed");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(jsonObject.toString());
        }
        jsonObject.addProperty("message", "Create successfully");
        return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/{id:\\d+}/task/history", method = RequestMethod.GET)
    public ResponseEntity<Object> getHistoryTask(@PathVariable("id") Long userId) {
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<Task> result = taskService.getHistoryTask(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(value = "/api/v1/user/{id:\\d+}/history/date", method = RequestMethod.GET)
    public ResponseEntity<Object> getHistoryByDate(@RequestParam("start") LocalDate start,
                                                   @RequestParam("end") LocalDate end,
                                                   @PathVariable("id") Long userId) {
        JsonObject jsonObject = new JsonObject();
        if (userId == null) {
            jsonObject.addProperty("message", "User id cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        if (start == null) {
            jsonObject.addProperty("message", "Start date cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        if (end == null) {
            jsonObject.addProperty("message", "End date cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        List<Task> result = taskService.getHistoryByDate(userId, start, end);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/{id:\\d+}/history/status", method = RequestMethod.GET)
    public ResponseEntity<Object> getHistoryByStatus(@RequestParam("status") Task.TaskStatus status,
                                                     @PathVariable("id") Long userId) {
        JsonObject jsonObject = new JsonObject();
        if (userId == null) {
            jsonObject.addProperty("message", "User id cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        if (status == null) {
            jsonObject.addProperty("message", "Status cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        List<Task> result = taskService.getHistoryByStatus(userId, status);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/{id:\\d+}/history", method = RequestMethod.GET)
    public ResponseEntity<Object> getHistoryByUserId(@RequestParam("start") LocalDate start,
                                                     @RequestParam("end") LocalDate end,
                                                     @PathVariable("id") Long userId,
                                                     @RequestParam("status") Task.TaskStatus status) {
        JsonObject jsonObject = new JsonObject();
        if (userId == null) {
            jsonObject.addProperty("message", "User id cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        if (start == null) {
            jsonObject.addProperty("message", "Start date cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        if (end == null) {
            jsonObject.addProperty("message", "End date cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        if (status == null) {
            jsonObject.addProperty("message", "Status cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        List<Task> result = taskService.getHistoryByUserId(userId, start, end, status);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
