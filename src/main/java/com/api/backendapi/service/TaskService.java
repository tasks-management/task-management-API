package com.api.backendapi.service;

import com.api.backendapi.dtos.CreateTaskDTO;
import com.api.backendapi.entity.Task;
import com.api.backendapi.repository.TaskRepository;
import com.api.backendapi.service.iservice.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TaskService implements ITaskService {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<Task> getHistoryTask(Long id) {
        return taskRepository.getHistoryTask(id);
    }

    // NOTE
    @Override
    public List<Task> getInProgressTaskByUserId(Long id) {
        return taskRepository.getInProgressTaskByUserId(id);
    }

    @Override
    public List<Task> getHistoryByDate(Long id, Date start, Date end) {
        return taskRepository.getHistoryTaskByDate(id, start, end);
    }

    @Override
    public List<Task> getHistoryByStatus(Long id, String status) {
        return taskRepository.getHistoryByStatus(id, status);
    }

    @Override
    public List<Task> getHistoryByUserId(Long id, Date startDate, Date endDate, String status) {
        return taskRepository.getHistoryByUserId(id, status, startDate, endDate);
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskByTaskID(Long taskId) {
        return taskRepository.findById(taskId).get();
    }

    @Override
    public List<Task> getAllSubmitedTaskForManager(Long userId) {
        return taskRepository.getAllSubmitedTaskForManager(userId);
    }

    @Override
    public List<Task> getAllPendingTaskForManager(Long userId) {
        return taskRepository.getAllPendingTaskForManager(userId);
    }
}
