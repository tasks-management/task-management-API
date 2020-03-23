package com.api.backendapi.service;

import com.api.backendapi.config.AppConfig;
import com.api.backendapi.dtos.CreateTaskDTO;
import com.api.backendapi.entity.Task;
import com.api.backendapi.repository.TaskRepository;
import com.api.backendapi.service.iservice.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class TaskService implements ITaskService {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<Task> getHistoryTask(Long id) {
        List<Task> result = taskRepository.getHistoryTask(id);
        if (result.size() != 0) {
            for (Task task : result) {
                if (task.getImage() != null) {
                    try {
                        File userDirectory = new File("images");
                        if (userDirectory.exists()) {
                            String filePath = userDirectory.getAbsolutePath() + "\\";
                            String fileNamePath = filePath + task.getImage();
                            String imageBase64String = AppConfig.encodeImageToBase64String(fileNamePath);
                            task.setImage(imageBase64String);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    // NOTE
    @Override
    public List<Task> getInProgressTaskByUserId(Long id) {
        return taskRepository.getInProgressTaskByUserId(id);
    }

    @Override
    public List<Task> getHistoryByDate(Long id, Date start, Date end) {
        List<Task> result = taskRepository.getHistoryTaskByDate(id, start, end);
        if (result.size() != 0) {
            for (Task task : result) {
                if (task.getImage() != null) {
                    try {
                        File userDirectory = new File("images");
                        if (userDirectory.exists()) {
                            String filePath = userDirectory.getAbsolutePath() + "\\";
                            String fileNamePath = filePath + task.getImage();
                            String imageBase64String = AppConfig.encodeImageToBase64String(fileNamePath);
                            task.setImage(imageBase64String);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<Task> getHistoryByStatus(Long id, String status) {
        List<Task> result = taskRepository.getHistoryByStatus(id, status);
        if (result.size() != 0) {
            for (Task task : result) {
                if (task.getImage() != null) {
                    try {
                        File userDirectory = new File("images");
                        if (userDirectory.exists()) {
                            String filePath = userDirectory.getAbsolutePath() + "\\";
                            String fileNamePath = filePath + task.getImage();
                            String imageBase64String = AppConfig.encodeImageToBase64String(fileNamePath);
                            task.setImage(imageBase64String);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<Task> getHistoryByUserId(Long id, Date startDate, Date endDate, String status) {
        List<Task> result = taskRepository.getHistoryByUserId(id, status, startDate, endDate);
        if (result.size() != 0) {
            for (Task task : result) {
                if (task.getImage() != null) {
                    try {
                        File userDirectory = new File("images");
                        if (userDirectory.exists()) {
                            String filePath = userDirectory.getAbsolutePath() + "\\";
                            String fileNamePath = filePath + task.getImage();
                            String imageBase64String = AppConfig.encodeImageToBase64String(fileNamePath);
                            task.setImage(imageBase64String);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
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
        List<Task> result = taskRepository.getAllSubmitedTaskForManager(userId);
        for (Task task: result) {
            if (task.getImage() != null) {
                try {
                    File userDirectory = new File("images");
                    if (userDirectory.exists()) {
                        String filePath = userDirectory.getAbsolutePath() + "\\";
                        String fileNamePath = filePath + task.getImage();
                        String imageBase64String = AppConfig.encodeImageToBase64String(fileNamePath);
                        task.setImage(imageBase64String);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public List<Task> getAllPendingTaskForManager(Long userId) {
        return taskRepository.getAllPendingTaskForManager(userId);
    }
}
