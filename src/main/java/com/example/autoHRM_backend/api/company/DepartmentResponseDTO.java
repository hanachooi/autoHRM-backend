package com.example.autoHRM_backend.api.company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentResponseDTO {

    private Long departmentId;

    private String departmentName;

    public DepartmentResponseDTO(Long id, String name) {
        this.departmentId = id;
        this.departmentName = name;
    }

}
