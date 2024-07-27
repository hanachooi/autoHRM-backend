package com.example.autoHRM_backend.domain.salary;

import com.example.autoHRM_backend.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@RequiredArgsConstructor
public class BaseSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long baseSalary;

    private Long year;

    private Long month;

    private Long workHour;

    private Long wage;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Builder
    protected BaseSalary(Long baseSalary, Long year, Long month, Long workHour, Employee employee, Long wage) {
        this.baseSalary = baseSalary;
        this.year = year;
        this.month = month;
        this.workHour = workHour;
        this.employee = employee;
        this.wage = wage;
    }


}
