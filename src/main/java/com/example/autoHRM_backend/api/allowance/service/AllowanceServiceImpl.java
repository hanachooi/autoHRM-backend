package com.example.autoHRM_backend.api.allowance.service;

import com.example.autoHRM_backend.domain.allowance.Allowance;
import com.example.autoHRM_backend.domain.allowance.AllowanceRepository;
import com.example.autoHRM_backend.domain.allowance.NightAllowance;
import com.example.autoHRM_backend.domain.allowance.OverTimeAllowance;
import com.example.autoHRM_backend.domain.commute.Commute;
import com.example.autoHRM_backend.domain.commute.DayOffCommute;
import com.example.autoHRM_backend.domain.commute.HolidayCommute;
import com.example.autoHRM_backend.domain.commute.WorkCommute;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.salary.BaseSalary;
import com.example.autoHRM_backend.domain.salary.BaseSalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllowanceServiceImpl implements AllowanceService {

    private final double nightAddition = 0.5;
    private final double overAddition = 0.5;
    private final BaseSalaryRepository baseSalaryRepository;
    private final AllowanceRepository allowanceRepository;


    @Override
    public void createNightAllowance(Commute commute) {

        Long time = commute.getNightTime();
        Employee employee = commute.getEmployee();

        Long minuteWage = calculateMinuteWage(employee);
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
        if (commute instanceof WorkCommute) {
            calculateWorkOverAllowance((WorkCommute) commute);
        } else if (commute instanceof HolidayCommute) {
            calculateHolidayOverAllowance((HolidayCommute) commute);
        } else if (commute instanceof DayOffCommute) {
            calculateDayOffOverAllowance((DayOffCommute) commute);
        }
    }

    private void calculateDayOffOverAllowance(DayOffCommute commute) {



    }

    private void calculateHolidayOverAllowance(HolidayCommute commute) {



    }

    public void calculateWorkOverAllowance(WorkCommute commute) {

        Long minuteWage = calculateMinuteWage(commute.getEmployee());
        Long allowancePay = calculateOverAllowance(commute.getOvertime(), minuteWage);
        Allowance allowance = OverTimeAllowance.builder()
                .commute(commute)
                .time(commute.getOvertime())
                .allowancePay(allowancePay)
                .build();
        allowanceRepository.save(allowance);
    }

    public Long calculateNightAllowance(Long time, Long minuteWage) {

        long allowancePay = (long)((time/10)*minuteWage*nightAddition);
        System.out.println("AllowanceServiceImpl.calculateNightAllowance" + allowancePay);

        return allowancePay;

    }

    public Long calculateOverAllowance(Long time, Long minuteWage){
        long allowancePay = (long)((time/10)*minuteWage*overAddition);
        System.out.println("AllowanceServiceImpl.calculateOverAllowance" + allowancePay);

        return allowancePay;
    }

    // 10분의 분급
    public Long calculateMinuteWage(Employee employee){
        BaseSalary baseSalary = baseSalaryRepository.findByEmployee(employee);
        Long wage = baseSalary.getWage();
        Long minuteWage = (long)(wage/6);
        System.out.println("AllowanceServiceImpl.calculateMinuteWage" + minuteWage);
        return minuteWage;
    }
}
