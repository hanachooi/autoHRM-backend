package com.example.autoHRM_backend.domain.commute;

import com.example.autoHRM_backend.domain.calendar.ScheduleType;
import com.example.autoHRM_backend.domain.employee.Employee;

import java.time.LocalDateTime;

public class CommuteFactory {
    public static Commute createCommute(ScheduleType type, LocalDateTime startTime, Employee employee) {
        switch (type) {
            case WORK:
                return WorkCommute.builder()
                        .startTime(startTime)
                        .employee(employee)
                        .status(true)
                        .build();
            case HOLIDAY:
                return HolidayCommute.builder()
                        .startTime(startTime)
                        .employee(employee)
                        .status(true)
                        .build();
            case DAYOFF:
                return DayOffCommute.builder()
                        .startTime(startTime)
                        .employee(employee)
                        .status(true)
                        .build();
            default:
                throw new IllegalArgumentException("Unknown ScheduleType: " + type);
        }
    }
}