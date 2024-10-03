package com.example.autoHRM_backend.auth.service;

import com.example.autoHRM_backend.auth.dto.JoinDTO;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(EmployeeRepository employeeRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String name = joinDTO.getName();
        String email = joinDTO.getEmail();
        String password = joinDTO.getPassword();

        Boolean isExist = employeeRepository.existsByEmail(email);

        if (isExist) {

            return;
        }

        Employee data = new Employee();

        data.setName(name);
        data.setEmail(email);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        employeeRepository.save(data);
    }
}
