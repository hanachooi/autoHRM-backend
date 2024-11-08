package com.example.autoHRM_backend.api.salary.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SalariesResponseDTO {

    // 사원명, 사원이메일, 년, 월, 기본급, 월급, 미지급금, 납부여부
    private String employeeName;
    private String employeeEmail;
    private List<SalaryDTO> salaries;

    public SalariesResponseDTO(String employeeName, String employeeEmail, List<SalaryDTO> salaries) {
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.salaries = salaries;
    }

}
