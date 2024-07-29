package com.example.autoHRM_backend.api.allowance.service;

import com.example.autoHRM_backend.domain.allowance.*;
import com.example.autoHRM_backend.domain.commute.Commute;
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
    private final double holidayAddition = 1.5;
    private final BaseSalaryRepository baseSalaryRepository;
    private final AllowanceRepository allowanceRepository;


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

        Long allowancePay = calculateOverAllowance(time, minuteWage);
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

    public Long calculateHolidayAllowance(Long time, Long minuteWage){
        long allowancePay = (long)((time/10)*minuteWage*holidayAddition);

        return allowancePay;
    }

}
