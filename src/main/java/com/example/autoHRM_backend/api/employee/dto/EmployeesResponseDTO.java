package com.example.autoHRM_backend.api.employee.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class EmployeesResponseDTO {

    private String name;

    private String email;

    private String departmentName;

    public EmployeesResponseDTO(String name, String email, String departmentName) {
        this.name = name;
        this.email = email;
        this.departmentName = departmentName;
    }

}
