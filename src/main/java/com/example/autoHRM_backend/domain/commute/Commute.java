package com.example.autoHRM_backend.domain.commute;

import com.example.autoHRM_backend.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Entity
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
public class Commute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

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
