package com.example.autoHRM_backend.api.calendar.service;

import com.example.autoHRM_backend.api.calendar.dto.ScheduleRequestDTO;
import com.example.autoHRM_backend.domain.calendar.DayOfWeek;
import com.example.autoHRM_backend.domain.calendar.ScheduleType;
import com.example.autoHRM_backend.domain.calendar.WeeklySchedule;
import com.example.autoHRM_backend.domain.calendar.WeeklyScheduleRepository;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final EmployeeRepository employeeRepository;
    private final WeeklyScheduleRepository weeklyScheduleRepository;

    public void createSchedule(ScheduleRequestDTO scheduleRequestDTO){
        Employee employee = employeeRepository.findByEmail(scheduleRequestDTO.getEmail());


        for (Map.Entry<DayOfWeek, ScheduleType> entry : scheduleRequestDTO.getSchedule().entrySet()) {

            WeeklySchedule weeklySchedule = scheduleRequestDTO.toEntity(employee, entry.getKey(), entry.getValue());
            weeklyScheduleRepository.save(weeklySchedule);

        }
    }

}
