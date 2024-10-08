package com.example.autoHRM_backend.api.salary.controller;

import com.example.autoHRM_backend.api.salary.dto.BaseSalaryRequestDTO;
import com.example.autoHRM_backend.api.salary.service.BaseSalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final BaseSalaryService baseSalaryService;

    @PostMapping("/")
    public ResponseEntity<Void> createBaseSalary(@RequestBody BaseSalaryRequestDTO baseSalaryRequestDTO){
        baseSalaryService.createBaseSalary(baseSalaryRequestDTO);

        return ResponseEntity.ok().build();
    }

}
