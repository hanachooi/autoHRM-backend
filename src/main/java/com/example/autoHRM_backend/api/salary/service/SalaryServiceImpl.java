package com.example.autoHRM_backend.api.salary.service;

import com.example.autoHRM_backend.api.salary.dto.BaseSalaryRequestDTO;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import com.example.autoHRM_backend.domain.salary.BaseSalary;
import com.example.autoHRM_backend.domain.salary.BaseSalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final EmployeeRepository employeeRepository;
    private final BaseSalaryRepository baseSalaryRepository;


    @Override
    public void createBaseSalary(BaseSalaryRequestDTO baseSalaryRequestDTO) {
        Employee employee = employeeRepository.findByEmail(baseSalaryRequestDTO.getEmail());

        BaseSalary baseSalary = baseSalaryRequestDTO.toEntity(employee, baseSalaryRequestDTO);
        baseSalaryRepository.save(baseSalary);
    }
}
