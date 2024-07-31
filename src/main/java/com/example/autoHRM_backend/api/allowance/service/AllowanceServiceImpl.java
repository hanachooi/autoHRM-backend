package com.example.autoHRM_backend.api.allowance.service;

import com.example.autoHRM_backend.domain.allowance.*;
import com.example.autoHRM_backend.domain.commute.*;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.salary.BaseSalary;
import com.example.autoHRM_backend.domain.salary.BaseSalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllowanceServiceImpl implements AllowanceService {

    private final double nightAddition = 0.5;
    private final double holidayAddition = 1.5;
    private final BaseSalaryRepository baseSalaryRepository;
    private final AllowanceRepository allowanceRepository;
    private final CommuteRepository commuteRepository;


    @Override
    public void createNightAllowance(Commute commute) {

        Long time = commute.getNightTime();
        Employee employee = commute.getEmployee();
        BaseSalary baseSalary = baseSalaryRepository.findByEmployee(employee);

        Long minuteWage = baseSalary.getMinuteWage();
        Long allowancePay = calculateNightAllowance(time, minuteWage);

        Allowance allowance = NightAllowance.builder()
                .commute(commute)
                .time(time)
                .allowancePay(allowancePay)
                .build();
        allowanceRepository.save(allowance);

    }

    @Override
    public void createOverAllowance(Commute commute) {

        Employee employee = commute.getEmployee();
        BaseSalary baseSalary = baseSalaryRepository.findByEmployee(employee);
        Long time = commute.getOverTime();
        Long minuteWage = baseSalary.getMinuteWage();
        double overAddition = 0;
        if(commute instanceof WorkCommute) {
            overAddition = 1.5;
        }else if(commute instanceof HolidayCommute){
            overAddition = 0.5;
        }

        Long allowancePay = calculateOverAllowance(time, minuteWage, overAddition);

        Allowance allowance = OverAllowance.builder()
                .commute(commute)
                .time(time)
                .allowancePay(allowancePay)
                .build();
        allowanceRepository.save(allowance);
    }

    @Override
    public void createHolidayAllowance(Commute commute) {
        Employee employee = commute.getEmployee();
        BaseSalary baseSalary = baseSalaryRepository.findByEmployee(employee);
        Long time = commute.getTime();
        Long minuteWage = baseSalary.getMinuteWage();
        Long allowancePay = calculateHolidayAllowance(time, minuteWage);

        Allowance allowance = HolidayAllowance.builder()
                .commute(commute)
                .time(time)
                .allowancePay(allowancePay)
                .build();
        allowanceRepository.save(allowance);
    }

//    // 월요일 오전 0 시
//    @Scheduled(cron = "0 0 0 * * 1", zone = "Asia/Seoul")
//    public void createDayOffAllowance() {
//
//        Employee employee = commute.getEmployee();
//        BaseSalary baseSalary = baseSalaryRepository.findByEmployee(employee);
//        Long time = commute.getTime();
//        Long minuteWage = baseSalary.getMinuteWage();
//
//        LocalDate today = LocalDate.now();
//        LocalDate startOfWeek = today.minusWeeks(1).with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
//        LocalDate endOfWeek = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY));
//
//        LocalDateTime startDateTime = startOfWeek.atStartOfDay();
//        LocalDateTime endDateTime = endOfWeek.atTime(LocalTime.MAX);
//
//        List<Commute> commutes = commuteRepository.findCommutesBetween(startDateTime, endDateTime);
//
//        long minute = 0L;
//        long dminute = 0L;
//
//        // 근무일 시간 총합
//        for (Commute commute : commutes) {
//            if(commute instanceof WorkCommute){
//                if(commute.getTime() > 540){
//                    minute = minute + 480;
//                }else{
//                    minute = minute + commute.getTime();
//                }
//            }
//            if(commute instanceof DayOffCommute){
//                dminute = dminute + commute.getTime();
//            }
//        }
//
//        if(minute < 2400){
//            dminute = minute + dminute - 2400;
//        }
//
//        calculateOverAllowance(dminute, minuteWage);
//
//
//    }

    public Long calculateNightAllowance(Long time, Long minuteWage) {

        long allowancePay = (long)((time/10)*minuteWage*nightAddition);
        System.out.println("AllowanceServiceImpl.calculateNightAllowance" + allowancePay);

        return allowancePay;

    }

    public Long calculateOverAllowance(Long time, Long minuteWage, double overAddition){
        long allowancePay = (long)((time/10)*minuteWage*overAddition);
        System.out.println("AllowanceServiceImpl.calculateOverAllowance" + allowancePay);

        return allowancePay;
    }

    public Long calculateHolidayAllowance(Long time, Long minuteWage){
        long allowancePay = (long)((time/10)*minuteWage*holidayAddition);

        return allowancePay;
    }

}
