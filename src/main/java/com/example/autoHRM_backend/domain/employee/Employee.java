package com.example.autoHRM_backend.domain.employee;

import com.example.autoHRM_backend.domain.calendar.WeeklySchedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String role;

    @OneToMany(mappedBy = "employee")
    private List<WeeklySchedule> weeklySchedule;
}
