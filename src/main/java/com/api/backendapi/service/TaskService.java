package com.api.backendapi.service;

import com.api.backendapi.entity.Task;
import com.api.backendapi.repository.TaskRepository;
import com.api.backendapi.service.iservice.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService implements ITaskService {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<Task> getHistoryTask(Long id) {
        return taskRepository.getHistoryTask(id);
    }

    @Override
    public List<Task> getTaskListFromUserId(Long id) {
        return taskRepository.getTaskListFromUserId(id);
    }

    @Override
    public List<Task> getHistoryByDate(Long id, LocalDate start, LocalDate end) {
        return taskRepository.getHistoryTaskByDate(id, start, end);
    }

    @Override
    public List<Task> getHistoryByStatus(Long id, Task.TaskStatus status) {
        String taskStatus = status.toString();
        return taskRepository.getHistoryByStatus(id, taskStatus);
    }

    @Override
    public List<Task> getHistoryByUserId(Long id, LocalDate startDate, LocalDate endDate, Task.TaskStatus status) {
        String taskStatus = status.toString();
        return taskRepository.getHistoryByUserId(id, taskStatus, startDate, endDate);
    }
}
