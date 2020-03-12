package com.api.backendapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CreateUserDTO implements Serializable {

    private String username;
    private String password;
    private String teamId;
    private String fullName;

    public CreateUserDTO() {
    }
}
