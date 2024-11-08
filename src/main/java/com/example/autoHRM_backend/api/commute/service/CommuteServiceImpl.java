package com.example.autoHRM_backend.api.commute.service;

import com.example.autoHRM_backend.api.allowance.service.AllowanceService;
import com.example.autoHRM_backend.api.calendar.util.DateUtil;
import com.example.autoHRM_backend.api.commute.dto.CommuteRequestDTO;
import com.example.autoHRM_backend.api.commute.dto.CommuteResponseDTO;
import com.example.autoHRM_backend.domain.calendar.DayOfWeek;
import com.example.autoHRM_backend.domain.calendar.ScheduleType;
import com.example.autoHRM_backend.domain.calendar.WeeklySchedule;
import com.example.autoHRM_backend.domain.calendar.WeeklyScheduleRepository;
import com.example.autoHRM_backend.domain.commute.*;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommuteServiceImpl implements CommuteService {

    private final EmployeeRepository employeeRepository;
    private final CommuteRepository commuteRepository;
    private final WeeklyScheduleRepository weeklyScheduleRepository;
    private final CommuteQueryRepository commuteQueryRepository;
    DateUtil dateUtil = new DateUtil();
    private final AllowanceService allowanceService;

    @Override
    public void checkIn(String email){

        Employee employee = employeeRepository.findByEmail(email);
        LocalDateTime startTime = LocalDateTime.now();
//        LocalDateTime startTime = LocalDateTime.of(2024, 10, 3, 8, 0);
        ScheduleType type = getType(startTime, employee);
        Commute commute = CommuteFactory.createCommute(type, startTime, employee);

        commuteRepository.save(commute);
    }

    @Override
    public void checkOut(String email) {
//        LocalDate today = LocalDate.of(2024, 10, 3);
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        List<Commute> commutes = commuteRepository.findCommutes(email, today, yesterday);
        if (commutes.isEmpty()) {
            System.out.println("CommuteServiceImpl.checkOut no commute start");
        }
        Commute commute = commutes.get(0);
        commute.checkOut();
        commuteRepository.save(commute);

        Long overtime = calculateOverTime(commute.getStartTime(), commute.getEndTime());
        Long nightTime = calculateNightTime(commute.getStartTime(), commute.getEndTime());
        Long time = calculateTime(commute.getStartTime(), commute.getEndTime());
        commute.setTime(overtime,nightTime, time);
        commuteRepository.save(commute);

        // 모든 케이스 공통 적용
        if(nightTime > 0 ){
        allowanceService.createNightAllowance(commute);
        }

        if(commute instanceof WorkCommute){
            if(overtime > 0){
            allowanceService.createOverAllowance(commute);
            }
        }else if(commute instanceof HolidayCommute){
            if(overtime > 0){
                allowanceService.createOverAllowance(commute);
            }
            allowanceService.createHolidayAllowance(commute);
        }
        // 휴무일 연장 수당은 배치로 구현
    }
    @Override
    public boolean checkInStatus(String email) {

        Commute commute = commuteQueryRepository.checkInStatus(email);
        boolean status = commute.isStatus();
        return status;
    }

    @Override
    public List<CommuteResponseDTO> findCommute(String employeeLoginId, String filterType, LocalDateTime startDate){

        System.out.println("CommuteServiceImpl.findCommute");

        LocalDateTime endDate = startDate;

        if (filterType.equals("week")) {
            // 주별 필터 적용 (예: startDate부터 7일간)
            List<Commute> commutes = commuteRepository.findCommutesBetween(startDate, endDate.plusDays(7), employeeLoginId);
            List<CommuteResponseDTO> commuteResponseDTOs = new ArrayList<>();

            for(Commute commute : commutes) {
                commuteResponseDTOs.add(new CommuteResponseDTO(commute));
            }

            return commuteResponseDTOs;

        } else if (filterType.equals("month")) {
            // 월별 필터 적용 (예: startDate부터 한 달간)
            List<Commute> commutes = commuteRepository.findCommutesBetween(startDate, endDate.plusMonths(1), employeeLoginId);
            List<CommuteResponseDTO> commuteResponseDTOs = new ArrayList<>();

            for(Commute commute : commutes) {
                commuteResponseDTOs.add(new CommuteResponseDTO(commute));
            }

            return commuteResponseDTOs;
        }
        return new ArrayList<>();
    }


    public ScheduleType getType(LocalDateTime localDateTime, Employee employee){
        DayOfWeek dayofweek = DayOfWeek.valueOf(dateUtil.getKoreanDayOfWeek(localDateTime));
        WeeklySchedule weeklySchedule = weeklyScheduleRepository.findByDayOfWeekAndEmployee(dayofweek, employee);
        return weeklySchedule.getScheduleType();
    }

    // 야간 수당 근무 분 추출
    public Long calculateNightTime(LocalDateTime start, LocalDateTime end) {
        LocalTime nightStart = LocalTime.of(22, 0); // 오후 10시
        LocalTime nightEnd = LocalTime.of(6, 0); // 오전 6시

        long totalNightMinutes = 0;

        while (start.isBefore(end)) {
            LocalDateTime currentNightStart = LocalDateTime.of(start.toLocalDate(), nightStart);
            LocalDateTime currentNightEnd = LocalDateTime.of(start.toLocalDate().plusDays(1), nightEnd);

            if (start.isAfter(currentNightEnd)) {
                currentNightStart = currentNightStart.plusDays(1);
                currentNightEnd = currentNightEnd.plusDays(1);
            }

            LocalDateTime periodStart = start.isAfter(currentNightStart) ? start : currentNightStart;
            LocalDateTime periodEnd = end.isBefore(currentNightEnd) ? end : currentNightEnd;

            if (periodStart.isBefore(periodEnd)) {
                long minutes = ChronoUnit.MINUTES.between(periodStart, periodEnd);
                totalNightMinutes += minutes;
            }

            start = currentNightEnd;
        }
        return ((totalNightMinutes + 5) / 10) * 10;
    }

    // 연장근무 분 추출
    public Long calculateOverTime(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);

        // 12시간이면 720시간 연장 -> 720 - 450 =


        if(duration.toMinutes() - 450 > 0){
            // 60분 9시간
            return ((duration.toMinutes() - 450 + 5) / 10) * 10;
        }
        return 0L;
    }

    // 근무시간 추출
    public Long calculateTime(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        if(duration.toMinutes() - 450 > 0){
            return duration.toMinutes() - 60;
        }
        return duration.toMinutes();
    }

}
