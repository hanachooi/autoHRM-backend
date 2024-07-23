package com.example.autoHRM_backend.domain.commute;

import com.example.autoHRM_backend.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Entity
public class Commute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Builder
    protected Commute(LocalDateTime startTime, Employee employee) {
        this.startTime = startTime;
        this.employee = employee;
    }

    public void checkOut() {
        if (endTime != null) {
            throw new IllegalArgumentException("이미 퇴근처리가 되었습니다");
        }
        this.endTime = LocalDateTime.now();

    }
}
