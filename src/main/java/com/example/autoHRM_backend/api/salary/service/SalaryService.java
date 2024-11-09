package com.example.autoHRM_backend.api.salary.service;

import com.example.autoHRM_backend.api.salary.dto.BaseSalaryRequestDTO;
import com.example.autoHRM_backend.api.salary.dto.SalariesResponseDTO;
import com.example.autoHRM_backend.api.salary.dto.SalaryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalaryService {

    void createBaseSalary(BaseSalaryRequestDTO baseSalaryRequestDTO);

    SalaryResponseDTO findMySalary(String employeeLoginId);

    List<SalariesResponseDTO> findMySalaries(String employeeLoginId, String email, Boolean status);

    void payOkSalary(Long salaryId, Long paiedAmount);
}
