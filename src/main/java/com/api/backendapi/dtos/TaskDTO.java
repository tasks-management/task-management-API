package com.api.backendapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class TaskDTO implements Serializable {

    private Long id;
    private String name;
    private String taskStatus;
    private String description;
    private String contentProcess;
    private Date startDate;
    private Date endDate;
    private Date timeComment;
    private String commentContent;
    private float rate;
    private Date created;
    private Long creatorId;
    private String image;
    private Long handlerId;
    private Date lastModified;

    public TaskDTO() {
    }
}
