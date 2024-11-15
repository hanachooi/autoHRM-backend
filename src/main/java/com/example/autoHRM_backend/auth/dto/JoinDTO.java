package com.example.autoHRM_backend.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class JoinDTO {

    private String email;
    private String name;

    private String password;

    private String role;

    private Long departmentId;

    private String companyName;

    public JoinDTO(String email, String name, String password, String role, Long departmentId, String companyName) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.departmentId = departmentId;
        this.companyName = companyName;
    }


}
