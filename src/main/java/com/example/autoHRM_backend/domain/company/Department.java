package com.example.autoHRM_backend.domain.company;

import com.example.autoHRM_backend.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id")
    private Long id;
    private String departmentName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    // 양방향 조회를 위한 것이므로 이건 필드에 포함 되는 것이 아님.
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    public Department(String departmentName, Company company) {
        this.departmentName = departmentName;
        this.company = company;
    }
}
