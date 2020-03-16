package com.api.backendapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TaskCommentDto implements Serializable {

    private String comment;
    private int rate;
    private String status;
}
