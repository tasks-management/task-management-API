package com.api.backendapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SubmitTaskDTO implements Serializable {
    private String status;
    private String imageBase64String;
}
