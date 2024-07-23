package com.example.autoHRM_backend.api.calendar.service;

import com.example.autoHRM_backend.api.calendar.dto.ScheduleDTO;
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

    public void createSchedule(ScheduleDTO scheduleDTO){
        Employee employee = employeeRepository.findByEmail(scheduleDTO.getEmail());


        for (Map.Entry<DayOfWeek, ScheduleType> entry : scheduleDTO.getSchedule().entrySet()) {

            WeeklySchedule weeklySchedule = scheduleDTO.toEntity(employee, entry.getKey(), entry.getValue());
            weeklyScheduleRepository.save(weeklySchedule);

        }
    }

}
