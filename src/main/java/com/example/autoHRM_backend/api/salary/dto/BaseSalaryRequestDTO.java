package com.example.autoHRM_backend.api.salary.dto;

import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.salary.BaseSalary;
import lombok.Getter;

@Getter
public class BaseSalaryRequestDTO {

    private String email;

    private Long year;

    private Long month;

    private Long baseSalary;

    private Long workHour;


    public BaseSalary toEntity(Employee employee, Long wage, BaseSalaryRequestDTO baseSalaryRequestDTO){
        return BaseSalary.builder()
                .baseSalary(baseSalaryRequestDTO.baseSalary)
                .year(baseSalaryRequestDTO.year)
                .month(baseSalaryRequestDTO.month)
                .workHour(baseSalaryRequestDTO.workHour)
                .employee(employee)
                .wage(wage)
                .build();
    }
}
