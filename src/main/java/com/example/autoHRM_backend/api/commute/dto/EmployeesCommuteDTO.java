package com.example.autoHRM_backend.api.commute.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter @Setter
public class EmployeesCommuteDTO {
    private String email;
    private String name;
    private List<CommuteResponseDTO> commutes;

    public EmployeesCommuteDTO(String email, String name, List<CommuteResponseDTO> commutes) {
        this.email = email;
        this.name = name;
        this.commutes = commutes;
    }

}
