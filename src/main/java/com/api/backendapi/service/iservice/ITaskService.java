package com.api.backendapi.service.iservice;

import com.api.backendapi.dtos.CreateTaskDTO;
import com.api.backendapi.entity.Task;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ITaskService {

    List<Task> getInProgressTaskByUserId(Long id);

    List<Task> getHistoryTask(Long id);

    List<Task> getHistoryByDate(Long id, Date start, Date end);

    List<Task> getHistoryByStatus(Long id, String status);

    List<Task> getHistoryByUserId(Long id, Date startDate, Date endDate, String status);

    Task createNewTask(Task task);

    Task getTaskDetail(Long taskId);

    List<Task> getAllSubmitedTaskForManager(Long userId);

    List<Task> getAllPendingTaskForManager(Long userId);
}
