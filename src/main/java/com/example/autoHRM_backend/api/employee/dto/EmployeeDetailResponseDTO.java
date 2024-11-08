package com.example.autoHRM_backend.api.employee.dto;


import com.example.autoHRM_backend.domain.calendar.DayOfWeek;
import com.example.autoHRM_backend.domain.calendar.ScheduleType;
import com.example.autoHRM_backend.domain.salary.Salary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
public class EmployeeDetailResponseDTO {

    private String name;

    private String email;

    private String companyName;

    private String departmentName;

    private Long baseSalary;

    private Long year;

    private Long month;

    private Long wage;

    private Long minuteWage;

    private Long salary;

    private Map<DayOfWeek, ScheduleType> weeklySchedule;

    public EmployeeDetailResponseDTO(String name, String email, String companyName, String departmentName) {
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.departmentName = departmentName;

    }

}
