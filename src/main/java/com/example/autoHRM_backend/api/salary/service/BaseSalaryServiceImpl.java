package com.example.autoHRM_backend.api.salary.service;

import com.example.autoHRM_backend.api.salary.dto.BaseSalaryRequestDTO;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import com.example.autoHRM_backend.domain.salary.BaseSalary;
import com.example.autoHRM_backend.domain.salary.BaseSalaryRepository;
import com.example.autoHRM_backend.domain.salary.Salary;
import com.example.autoHRM_backend.domain.salary.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseSalaryServiceImpl implements BaseSalaryService {

    private final EmployeeRepository employeeRepository;
    private final BaseSalaryRepository baseSalaryRepository;
    private final SalaryRepository salaryRepository;


    @Override
    public void createBaseSalary(BaseSalaryRequestDTO baseSalaryRequestDTO) {
        Employee employee = employeeRepository.findByEmail(baseSalaryRequestDTO.getEmail());

        Long wage = calculateWage(baseSalaryRequestDTO);
        Long minuteWage = calculateMinuteWage(wage);
        BaseSalary baseSalary = baseSalaryRequestDTO.toEntity(employee, wage, minuteWage, baseSalaryRequestDTO);
        baseSalaryRepository.save(baseSalary);


        Salary salary = createSalary(employee, baseSalaryRequestDTO);
        salaryRepository.save(salary);
    }

    public Salary createSalary(Employee employee, BaseSalaryRequestDTO baseSalaryRequestDTO){
        return Salary.builder()
                .employee(employee)
                .year(baseSalaryRequestDTO.getYear())
                .month(baseSalaryRequestDTO.getMonth())
                .salary(baseSalaryRequestDTO.getBaseSalary())
                .build();
    }

    public Long calculateWage(BaseSalaryRequestDTO baseSalaryRequestDTO){
        Long wage = (long)(baseSalaryRequestDTO.getBaseSalary() / (baseSalaryRequestDTO.getWorkHour()*8));
        return wage;
    }

    // 10분의 분급
    public Long calculateMinuteWage(Long wage){
        Long minuteWage = (long)(wage/6);
        return minuteWage;
    }
}
