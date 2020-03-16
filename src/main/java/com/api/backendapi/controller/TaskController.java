package com.api.backendapi.controller;

import com.api.backendapi.dtos.CreateTaskDTO;
import com.api.backendapi.dtos.TaskCommentDto;
import com.api.backendapi.entity.Task;
import com.api.backendapi.entity.User;
import com.api.backendapi.service.iservice.ITaskService;
import com.api.backendapi.service.iservice.IUserService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
public class TaskController {
    @Autowired
    ITaskService taskService;

    @Autowired
    IUserService userService;

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/{id:\\d+}/task/list", method = RequestMethod.GET)
    public ResponseEntity<Object> getTaskList(@PathVariable("id") Long userId) {
        JsonObject jsonObject = new JsonObject();
        if (userId == null) {
            jsonObject.addProperty("message", "Cannot find task list from that user");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(jsonObject.toString());
        }
        List<Task> result = taskService.getTaskListFromUserId(userId);
        if (result.size() == 0) {
            jsonObject.addProperty("message", "Don't have any task yet");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject.toString());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/task/create", method = RequestMethod.POST)
    public ResponseEntity<Object> createNewTask(@RequestBody CreateTaskDTO dto) {
        Date startDate, endDate;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            startDate = sdf.parse(dto.getStartDate());
            endDate = sdf.parse(dto.getEndDate());
        } catch (Exception e) {
            String errorMsg = "Cannot parse start date and end date. Please check again";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", errorMsg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        User creator = userService.getUserById(dto.getCreator());
        if (creator == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        User handler = userService.getUserById(dto.getHandler());
        if (handler == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Date createdDate = new Date();
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setContentProcess(dto.getProcess());
        task.setTaskStatus(dto.getStatus());
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setCreated(createdDate);
        task.setCreatorId(creator);
        task.setHandlerId(handler);
        Task result = taskService.createNewTask(task);
        if (result == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }


    //get submited task for manager to approve
    @ResponseBody
    @RequestMapping(value = "/api/v1/task/{id}/submitTasks", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllSubmitedTaskForManager(@PathVariable("id") Long userId) {
        List<Task> result = taskService.getAllSubmitedTaskForManager(userId);
        if (result.size() == 0) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "Don't have any submitted task");
            return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //get pending task for manager to approve
    @ResponseBody
    @RequestMapping(value = "/api/v1/task/{id}/pendingTasks", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllPendingTaskForManager(@PathVariable("id") Long userId) {
        List<Task> result = taskService.getAllPendingTaskForManager(userId);
        if (result.size() == 0) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "Don't have any submitted task");
            return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //get pending task, submitted tasks for manager (combine 2 method to one api )
    @ResponseBody
    @RequestMapping(value = "/api/v1/task/{id}/approveTasks", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllRequestedTaskForManager(@PathVariable("id") Long userId) {
        List<Task> pendingTask = taskService.getAllPendingTaskForManager(userId);
        List<Task> submitedTask = taskService.getAllSubmitedTaskForManager(userId);
        List<Task> result = new ArrayList<>();
        if (submitedTask.size() != 0) {
            for (Task submited : submitedTask) {
                result.add(submited);
            }
        }
        if (pendingTask.size() != 0) {
            for (Task pending : pendingTask) {
                result.add(pending);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/task/{id}/detail", method = RequestMethod.GET)
    public ResponseEntity<Object> getTaskDetail(@PathVariable("id") Long taskId) {
        return new ResponseEntity<>(taskService.getTaskDetail(taskId), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/task/{id}/acceptOrDecline", method = RequestMethod.PUT)
    public ResponseEntity<Object> acceptOrDeclineUserTask(@PathVariable("id") Long taskId,
                                                @RequestBody TaskCommentDto dto) {
        Task taskDto = taskService.getTaskDetail(taskId);
        taskDto.setCommentContent(dto.getComment());
        taskDto.setTaskStatus(dto.getStatus());
        taskDto.setRate(dto.getRate());
        taskDto.setTimeComment(new Date());
        Task result = taskService.createNewTask(taskDto);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/task/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> changeTaskStatus(@PathVariable("id") Long taskId,
                                                   @RequestParam("status") String status) {
        Task task = taskService.getTaskDetail(taskId);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            task.setTaskStatus(status);
            Task result =taskService.createNewTask(task);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
    }
}
