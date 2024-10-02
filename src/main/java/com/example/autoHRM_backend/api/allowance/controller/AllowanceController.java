package com.example.autoHRM_backend.api.allowance.controller;


import com.example.autoHRM_backend.api.allowance.dto.AllowanceCommuteResponseDTO;
import com.example.autoHRM_backend.api.allowance.service.AllowanceQueryService;
import com.example.autoHRM_backend.auth.service.EmployeeUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AllowanceController {

    private final AllowanceQueryService allowanceQueryService;

    @GetMapping("/allowance/my")
    public ResponseEntity<List<AllowanceCommuteResponseDTO>> findMonthlyAllowance(@RequestParam String startOfMonth, @RequestParam String endOfMonth) {
        String employeeLoginId = getEmployeeLoginId();

        LocalDateTime start = LocalDate.parse(startOfMonth).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endOfMonth).atStartOfDay();

        List<AllowanceCommuteResponseDTO> response = allowanceQueryService.findMonthlyAllowance(employeeLoginId, start, end);
        return ResponseEntity.ok(response);
    }

    private String getEmployeeLoginId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails) principal;

        return employeeUserDetails.getEmail();
    }

}
