package com.example.autoHRM_backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    private String email;
    private String name;

    private String password;

}
