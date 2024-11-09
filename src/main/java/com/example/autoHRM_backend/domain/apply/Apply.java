package com.example.autoHRM_backend.domain.apply;

import com.example.autoHRM_backend.domain.commute.Commute;
import com.example.autoHRM_backend.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "apply_id")
    private Long id;

    private LocalDateTime rectifyDate;

    private LocalDateTime applyDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String type;

    private String content;

    private String status;

    @ManyToOne()
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Apply(LocalDateTime rectifyDate, LocalDateTime applyDate, LocalDateTime startTime, LocalDateTime endTime, String type, Employee employee, String content, String status) {
        this.rectifyDate = rectifyDate;
        this.applyDate = applyDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.employee = employee;
        this.content = content;
        this.status = status;
    }

}
