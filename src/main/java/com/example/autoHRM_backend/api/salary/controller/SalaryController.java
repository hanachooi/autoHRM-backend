package com.example.autoHRM_backend.api.salary.controller;

import com.example.autoHRM_backend.api.salary.dto.BaseSalaryRequestDTO;
import com.example.autoHRM_backend.api.salary.dto.SalaryResponseDTO;
import com.example.autoHRM_backend.api.salary.service.SalaryService;
import com.example.autoHRM_backend.auth.service.EmployeeUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping("/salary")
    public ResponseEntity<Void> createBaseSalary(@RequestBody BaseSalaryRequestDTO baseSalaryRequestDTO){
        salaryService.createBaseSalary(baseSalaryRequestDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/salary/my")
    public ResponseEntity<SalaryResponseDTO> findMySalary(){
        String employeeLoginId = getEmployeeLoginId();
        SalaryResponseDTO dto = salaryService.findMySalary(employeeLoginId);

        return ResponseEntity.ok(dto);
    }

    private String getEmployeeLoginId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails) principal;

        return employeeUserDetails.getEmail();
    }

}
