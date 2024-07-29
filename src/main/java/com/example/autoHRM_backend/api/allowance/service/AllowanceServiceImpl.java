package com.example.autoHRM_backend.api.allowance.service;

import com.example.autoHRM_backend.domain.allowance.Allowance;
import com.example.autoHRM_backend.domain.allowance.AllowanceRepository;
import com.example.autoHRM_backend.domain.allowance.NightAllowance;
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
    public Long calculateNightAllowance(Long time, Long minuteWage) {

        long allowancePay = (long)((time/10)*minuteWage*nightAddition);
        System.out.println("AllowanceServiceImpl.calculateNightAllowance" + allowancePay);

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
