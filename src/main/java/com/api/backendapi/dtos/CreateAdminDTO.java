package com.api.backendapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CreateAdminDTO implements Serializable {

    private String username;
    private String password;
    private String teamName;
    private String fullName;

    public CreateAdminDTO() {
    }


}
