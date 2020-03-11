package com.api.backendapi.service.iservice;

import com.api.backendapi.entity.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ITaskService {

    List<Task> getTaskListFromUserId(Long id);

    List<Task> getHistoryTask(Long id);

    List<Task> getHistoryByDate(Long id, LocalDate start, LocalDate end);

    List<Task> getHistoryByStatus(Long id, Task.TaskStatus status);

    List<Task> getHistoryByUserId(Long id, LocalDate startDate, LocalDate endDate, Task.TaskStatus status);
}
