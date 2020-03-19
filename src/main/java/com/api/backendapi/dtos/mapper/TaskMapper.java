package com.api.backendapi.dtos.mapper;

import com.api.backendapi.dtos.TaskDTO;
import com.api.backendapi.entity.Task;

import java.io.Serializable;

public class TaskMapper implements Serializable {

    public static TaskDTO mapTaskToTaskDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setCommentContent(task.getCommentContent());
        dto.setContentProcess(task.getCommentContent());
        dto.setCreated(task.getCreated());
        dto.setCreatorId(task.getCreatorId().getId());
        dto.setDescription(task.getDescription());
        dto.setEndDate(task.getEndDate());
        dto.setHandlerId(task.getHandlerId().getId());
        dto.setId(task.getId());
        dto.setImage(task.getImage());
        dto.setLastModified(task.getLastModified());
        dto.setName(task.getName());
        dto.setRate(task.getRate());
        dto.setStartDate(task.getStartDate());
        dto.setTaskStatus(task.getTaskStatus());
        dto.setTimeComment(task.getTimeComment());
        return dto;
    }
}
