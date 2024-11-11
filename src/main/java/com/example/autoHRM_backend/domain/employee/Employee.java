package com.example.autoHRM_backend.domain.employee;

import com.example.autoHRM_backend.domain.apply.Apply;
import com.example.autoHRM_backend.domain.calendar.WeeklySchedule;
import com.example.autoHRM_backend.domain.company.Department;
import com.example.autoHRM_backend.domain.salary.BaseSalary;
import com.example.autoHRM_backend.domain.salary.Salary;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

    private String pass1;

    private String role;

    private String companyName;

    @OneToMany(mappedBy = "employee")
    private List<WeeklySchedule> weeklySchedule;

    @OneToOne(mappedBy = "employee")
    private BaseSalary baseSalary;

    @OneToMany(mappedBy = "employee")
    private List<Salary> salary;

    @OneToMany(mappedBy = "employee")
    private List<Apply> apply;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    @Column(columnDefinition = "JSON")
    private String faceData;  // 얼굴 데이터 추가

    private LocalDateTime lastLogin;  // 마지막 로그인 시간 추가

    private Boolean isActive = true;  // Django의 is_active
    private Boolean isStaff = false;  // Django의 is_staff
    private Boolean isSuperuser = false;  // Django의 is_superuser


}
