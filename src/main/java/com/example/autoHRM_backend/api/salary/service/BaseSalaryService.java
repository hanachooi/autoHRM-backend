package com.example.autoHRM_backend.api.salary.service;

import com.example.autoHRM_backend.api.salary.dto.BaseSalaryRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface BaseSalaryService {

    void createBaseSalary(BaseSalaryRequestDTO baseSalaryRequestDTO);

}
