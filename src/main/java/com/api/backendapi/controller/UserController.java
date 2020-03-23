package com.api.backendapi.controller;

import com.api.backendapi.dtos.CreateAdminDTO;
import com.api.backendapi.dtos.CreateUserDTO;
import com.api.backendapi.dtos.TaskDTO;
import com.api.backendapi.dtos.mapper.TaskMapper;
import com.api.backendapi.entity.Task;
import com.api.backendapi.entity.Team;
import com.api.backendapi.service.iservice.ITaskService;
import com.api.backendapi.service.iservice.ITeamService;
import com.google.gson.JsonObject;
import com.api.backendapi.entity.User;
import com.api.backendapi.service.iservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.TimeZone;

@RestController
public class UserController {

    @Autowired
    ITeamService teamService;

    @Autowired
    ITaskService taskService;

    @Autowired
    IUserService userService;

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/{id:\\d+}/task=InProgress", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllInProgressTasksByUserID(@PathVariable("id") Long userId) {
        JsonObject jsonObject = new JsonObject();
        List<Task> result = taskService.getInProgressTaskByUserId(userId);
        List<TaskDTO> responseResult = new ArrayList<>();
        if (result.size() == 0) {
            jsonObject.addProperty("message", "Don't have any task yet");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject.toString());
        }
        for (Task task: result) {
            responseResult.add(TaskMapper.mapTaskToTaskDTO(task));
        }
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/{id:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserInformation(@PathVariable("id") Long userId) {
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/v1/user/createManager", method = RequestMethod.POST)
    @ResponseBody
    private ResponseEntity<Object> createManager(@RequestBody CreateAdminDTO dto) {
        Team team = teamService.createNewTeam(dto.getTeamName());
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole("manager");
        user.setActive(true);
        user.setName(dto.getFullName());
        user.setTeam(team);
        user = userService.saveUser(user);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/v1/user/createUser", method = RequestMethod.POST)
    @ResponseBody
    private ResponseEntity<Object> createUser(@RequestBody CreateUserDTO dto) {
        Team team = teamService.findTeamByID(Long.parseLong(dto.getTeamId()));
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole("user");
        user.setActive(true);
        user.setName(dto.getFullName());
        user.setTeam(team);
        user = userService.saveUser(user);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

//    @RequestMapping(value = "/api/v1/user/createAdmin", method = RequestMethod.GET)
//    @ResponseBody
//    private ResponseEntity<Object> createAdmin() {
//        User user = new User();
//        user.setName("Admin");
//        user.setActive(true);
//        user.setRole("admin");
//        user.setUsername("admin");
//        user.setPassword("password");
//        if (userService.saveUser(user) == false) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

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
        User u = userService.saveUser(user);
        JsonObject jsonObject = new JsonObject();
        if (u == null) {
            jsonObject.addProperty("message", "Create failed");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(jsonObject.toString());
        }
        jsonObject.addProperty("message", "Create successfully");
        return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/{id:\\d+}/task/history", method = RequestMethod.GET)
    public ResponseEntity<Object> getHistoryTask(@RequestParam(value = "start", required = false) String start,
                                                 @RequestParam(value = "end", required = false) String end,
                                                 @PathVariable("id") Long userId,
                                                 @RequestParam(value = "status", required = false) String status) {
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (start != null && end != null){
            Date startDate, endDate;
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                startDate = sdf.parse(start);
                endDate = sdf.parse(end);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            if (status != null) {
                List<Task> result = taskService.getHistoryByUserId(userId, startDate, endDate, status);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                List<Task> result = taskService.getHistoryByDate(userId, startDate, endDate);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } else{
            if (status != null) {
                List<Task> result = taskService.getHistoryByStatus(userId, status);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                List<Task> result = taskService.getHistoryTask(userId);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
    }


//    @ResponseBody
//    @RequestMapping(value = "/api/v1/user/{id:\\d+}/history/date", method = RequestMethod.GET)
//    public ResponseEntity<Object> getHistoryByDate(@RequestParam("start") String start,
//                                                   @RequestParam("end") String end,
//                                                   @PathVariable("id") Long userId) {
//        JsonObject jsonObject = new JsonObject();
//        Date startDate, endDate;
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        try {
//            startDate = sdf.parse(start);
//            endDate = sdf.parse(end);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
//        }
//        if (userId == null) {
//            jsonObject.addProperty("message", "User id cannot be null");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
//        }
//        if (start == null) {
//            jsonObject.addProperty("message", "Start date cannot be null");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
//        }
//        if (end == null) {
//            jsonObject.addProperty("message", "End date cannot be null");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
//        }
//        List<Task> result = taskService.getHistoryByDate(userId, startDate, endDate);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

//    @ResponseBody
//    @RequestMapping(value = "/api/v1/user/{id:\\d+}/history/status", method = RequestMethod.GET)
//    public ResponseEntity<Object> getHistoryByStatus(@RequestParam("status") String status,
//                                                     @PathVariable("id") Long userId) {
//        JsonObject jsonObject = new JsonObject();
//        if (userId == null) {
//            jsonObject.addProperty("message", "User id cannot be null");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
//        }
//        if (status == null) {
//            jsonObject.addProperty("message", "Status cannot be null");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
//        }

//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

//    @ResponseBody
//    @RequestMapping(value = "/api/v1/user/{id:\\d+}/history", method = RequestMethod.GET)
//    public ResponseEntity<Object> getHistoryByUserId(@RequestParam("start") String start,
//                                                     @RequestParam("end") String end,
//                                                     @PathVariable("id") Long userId,
//                                                     @RequestParam("status") String status) {
//        JsonObject jsonObject = new JsonObject();
//        Date startDate, endDate;
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        try {
//            startDate = sdf.parse(start);
//            endDate = sdf.parse(end);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
//        }
//        if (userId == null) {
//            jsonObject.addProperty("message", "User id cannot be null");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
//        }
//        if (start == null) {
//            jsonObject.addProperty("message", "Start date cannot be null");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
//        }
//        if (end == null) {
//            jsonObject.addProperty("message", "End date cannot be null");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
//        }
//        if (status == null) {
//            jsonObject.addProperty("message", "Status cannot be null");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
//        }
//        List<Task> result = taskService.getHistoryByUserId(userId, startDate, endDate, status);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/managers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllManagerUser() {
        return new ResponseEntity<>(userService.getAllManagerUser(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/{id:\\d+}/users", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllUserInTeamByManagerID(@PathVariable("id") Long userId) {
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.getAllUserInTeam(userId), HttpStatus.OK);
    }
}
