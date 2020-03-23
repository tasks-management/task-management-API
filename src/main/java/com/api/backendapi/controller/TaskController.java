package com.api.backendapi.controller;

import com.api.backendapi.dtos.CreateTaskDTO;
import com.api.backendapi.dtos.ModifiedTaskDTO;
import com.api.backendapi.dtos.SubmitTaskDTO;
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

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        List<Task> result = taskService.getInProgressTaskByUserId(userId);
        if (result.size() == 0) {
            jsonObject.addProperty("message", "Don't have any task yet");
            return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
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
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setContentProcess(dto.getProcess());
        task.setTaskStatus(dto.getStatus());
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setCreated(new Date());
        task.setCreatorId(creator);
        task.setHandlerId(handler);
        task.setLastModified(new Date());
        Task result = taskService.saveTask(task);
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
            jsonObject.addProperty("message", "Don't have any pending task");
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
    public ResponseEntity<Object> getTaskByTaskID(@PathVariable("id") Long taskId) {
        return new ResponseEntity<>(taskService.getTaskByTaskID(taskId), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/task/{id}/acceptOrDecline", method = RequestMethod.PUT)
    public ResponseEntity<Object> acceptOrDeclineUserTask(@PathVariable("id") Long taskId,
                                                @RequestBody TaskCommentDto dto) {
        Task task = taskService.getTaskByTaskID(taskId);
        task.setCommentContent(dto.getComment());
        task.setTaskStatus(dto.getStatus());
        task.setRate(dto.getRate());
        task.setTimeComment(new Date());
        Task result = taskService.saveTask(task);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/task/status/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> changeTaskStatus(@PathVariable("id") Long taskId,
                                                   @RequestParam("status") String status) {
        Task task = taskService.getTaskByTaskID(taskId);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            task.setTaskStatus(status);
            task.setLastModified(new Date());
            Task result =taskService.saveTask(task);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/task/{id}/submit", method = RequestMethod.PUT)
    public ResponseEntity<Object> submitTaskFromUser(@PathVariable("id") Long taskId,
                                                     @RequestBody SubmitTaskDTO taskSubmitted) {
        Task taskSubmitedDetail = taskService.getTaskByTaskID(taskId);
        String status = taskSubmitted.getStatus();
        String imageFile = taskSubmitted.getImageBase64String();
        if (status.length() == 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (imageFile.length() == 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        taskSubmitedDetail.setTaskStatus(status);
        byte[] imageByte = Base64.getMimeDecoder().decode(imageFile);
        File userDirectory = new File("images");
        if (!userDirectory.exists()) {
            new File("images").mkdir();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endDateOfTask = sdf.format(new Date());
        String filePath = userDirectory.getAbsolutePath() + "\\";
        String fileName = taskSubmitedDetail.getHandlerId().getUsername() + "_" + taskSubmitedDetail.getId() + "_" + endDateOfTask + ".png";
        try {
            File dest = new File(filePath + fileName);
            FileOutputStream fos = new FileOutputStream(dest);
            fos.write(imageByte);
            if (fos != null) {
                fos.close();
            }
            taskSubmitedDetail.setImage(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Task result = taskService.saveTask(taskSubmitedDetail);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/task/{id}/modify", method = RequestMethod.PUT)
    public ResponseEntity<Object> modifyPendingTaskForUser(@PathVariable("id") Long taskId,
                                                    @RequestBody ModifiedTaskDTO taskModifyDto) {
        Task taskModified = taskService.getTaskByTaskID(taskId);
        if (taskModified == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        taskModified.setName(taskModifyDto.getName());
        taskModified.setDescription(taskModifyDto.getDescription());
        taskModified.setContentProcess(taskModifyDto.getProcess());
        Date startDate, endDate;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            startDate = sdf.parse(taskModifyDto.getStartDate());
            endDate = sdf.parse(taskModifyDto.getEndDate());
        } catch (Exception e) {
            String errorMsg = "Cannot parse start date and end date. Please check again";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", errorMsg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
        }
        taskModified.setStartDate(startDate);
        taskModified.setEndDate(endDate);
        taskModified.setTaskStatus(taskModifyDto.getStatus());
        taskModified.setLastModified(new Date());
        Task result = taskService.saveTask(taskModified);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
