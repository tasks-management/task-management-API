package com.api.backendapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CreateTaskDTO implements Serializable {
    private String name;
    private String description;
    private String process;
    private String status;
    private String startDate;
    private String endDate;
    private Long creator;
    private Long handler;
}
