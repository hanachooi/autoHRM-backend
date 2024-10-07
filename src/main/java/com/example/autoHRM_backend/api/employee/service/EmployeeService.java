package com.example.autoHRM_backend.api.employee.service;

import com.example.autoHRM_backend.api.employee.dto.EmployeeDetailResponseDTO;
import com.example.autoHRM_backend.api.employee.dto.EmployeesResponseDTO;
import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.company.CompanyRepository;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeQueryRepository;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final CompanyRepository companyRepository;
    private final EmployeeQueryRepository employeeQueryRepository;

    public List<EmployeesResponseDTO> findMyEmployees(Long departmentId, String employeeId, String employeeLoginId){

        Company company = companyRepository.findCompanyByEmployeeEmail(employeeLoginId);
        return employeeQueryRepository.findAllEmployees(departmentId, employeeId, company);

    }

    public EmployeeDetailResponseDTO findEmployeeDetail(String employeeEmail){

        EmployeeDetailResponseDTO dto = employeeQueryRepository.findEmployeeDetail(employeeEmail);
        System.out.println(dto);
        return dto;
    }

}
