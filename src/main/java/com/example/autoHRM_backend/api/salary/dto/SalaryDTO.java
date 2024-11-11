package com.example.autoHRM_backend.api.salary.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SalaryDTO {

    private Long id;
    private Long year;
    private Long month;
    private Long salary;
    private Long unpaid;
    private Boolean status;

    public SalaryDTO(Long id, Long year, Long month, Long salary, Long unpaid, Boolean status) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.salary = salary;
        this.unpaid = unpaid;
        this.status = status;
    }
}
