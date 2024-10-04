package com.example.autoHRM_backend.api.salary.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SalaryResponseDTO {

    private Long baseSalary;

    private Long salary;

    public SalaryResponseDTO(Long baseSalary, Long salary) {
        this.baseSalary = baseSalary;
        this.salary = salary;
    }

}
