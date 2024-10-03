package com.example.autoHRM_backend.api.calendar.dto;

import com.example.autoHRM_backend.domain.calendar.DayOfWeek;
import com.example.autoHRM_backend.domain.calendar.ScheduleType;
import com.example.autoHRM_backend.domain.calendar.WeeklySchedule;
import com.example.autoHRM_backend.domain.employee.Employee;
import lombok.Getter;

import java.util.Map;

@Getter
public class ScheduleRequestDTO {

    private String email;
    private Map<DayOfWeek, ScheduleType> schedule;

    public WeeklySchedule toEntity(Employee employee, DayOfWeek dayOfWeek, ScheduleType scheduleType){
        return WeeklySchedule.builder()
                .employee(employee)
                .dayOfWeek(dayOfWeek)
                .scheduleType(scheduleType)
                .build();
    }
}
