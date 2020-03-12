package com.api.backendapi.controller;

import com.api.backendapi.entity.Task;
import com.api.backendapi.service.iservice.ITaskService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    ITaskService taskService;

//    @ResponseBody
//    @RequestMapping(value = "/api/v1/user/{id:\\d+}/task/list", method = RequestMethod.GET)
//    public ResponseEntity<Object> getTaskList(@PathVariable("id") Long userId) {
//        JsonObject jsonObject = new JsonObject();
//        if (userId == null) {
//            jsonObject.addProperty("message", "Cannot find task list from that user");
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(jsonObject.toString());
//        }
//        List<Task> result = taskService.getInProgressTaskByUserId(userId);
//        if (result.size() == 0) {
//            jsonObject.addProperty("message", "Don't have any task yet");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject.toString());
//        }
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}
