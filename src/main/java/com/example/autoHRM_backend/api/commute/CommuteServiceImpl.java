package com.example.autoHRM_backend.api.commute;

import com.example.autoHRM_backend.api.calendar.util.DateUtil;
import com.example.autoHRM_backend.domain.calendar.DayOfWeek;
import com.example.autoHRM_backend.domain.calendar.ScheduleType;
import com.example.autoHRM_backend.domain.calendar.WeeklySchedule;
import com.example.autoHRM_backend.domain.calendar.WeeklyScheduleRepository;
import com.example.autoHRM_backend.domain.commute.*;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommuteServiceImpl implements CommuteService {

    private final EmployeeRepository employeeRepository;
    private final CommuteRepository commuteRepository;
    private final WeeklyScheduleRepository weeklyScheduleRepository;
    DateUtil dateUtil = new DateUtil();

    @Override
    public void checkIn(String email){

        Employee employee = employeeRepository.findByEmail(email);
        LocalDateTime startTime = LocalDateTime.now();
        ScheduleType type = getType(startTime);
        Commute commute;

        switch (type) {
            case WORK:
                commute = WorkCommute.builder()
                        .startTime(startTime)
                        .employee(employee)
                        .build();
                break;
            case HOLIDAY:
                commute = HolidayCommute.builder()
                        .startTime(startTime)
                        .employee(employee)
                        .build();
                break;
            case DAYOFF:
                commute = DayOffCommute.builder()
                        .startTime(startTime)
                        .employee(employee)
                        .build();
                break;
            default:
                throw new IllegalArgumentException("Unknown ScheduleType: " + type);
        }

        commuteRepository.save(commute);
    }

    @Override
    public void checkOut(String email) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        List<Commute> commutes = commuteRepository.findCommutes(email, today, yesterday);
        Commute commute = commutes.get(0);
        commute.checkOut();
        commuteRepository.save(commute);
    }

    public ScheduleType getType(LocalDateTime localDateTime){
        DayOfWeek dayofweek = DayOfWeek.valueOf(dateUtil.getKoreanDayOfWeek(localDateTime));
        WeeklySchedule weeklySchedule = weeklyScheduleRepository.findByDayOfWeek(dayofweek);
        return weeklySchedule.getScheduleType();
    }

}
