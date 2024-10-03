package com.example.autoHRM_backend.domain.calendar;

import com.example.autoHRM_backend.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class WeeklySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Builder
    private WeeklySchedule(Employee employee, DayOfWeek dayOfWeek, ScheduleType scheduleType) {
        this.employee = employee;
        this.dayOfWeek = dayOfWeek;
        this.scheduleType = scheduleType;
    }

}
