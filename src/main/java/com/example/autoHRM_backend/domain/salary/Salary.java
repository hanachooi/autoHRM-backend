package com.example.autoHRM_backend.domain.salary;

import com.example.autoHRM_backend.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long year;

    private Long month;

    private Long salary;

    private Long unpaid;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Builder
    protected Salary(Long year, Long month, Long salary, Employee employee, Boolean status, Long unpaid) {
        this.year = year;
        this.month = month;
        this.salary = salary;
        this.employee = employee;
        this.status = status;
        this.unpaid = unpaid;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
        this.unpaid = salary;
    }
}
