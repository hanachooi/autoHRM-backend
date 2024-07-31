package com.example.autoHRM_backend.api.allowance.controller;


import com.example.autoHRM_backend.api.allowance.dto.AllowanceResponseDTO;
import com.example.autoHRM_backend.api.allowance.service.AllowanceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/allowance")
@RequiredArgsConstructor
public class AllowanceController {

    private final AllowanceQueryService allowanceQueryService;

    @GetMapping("/")
    public ResponseEntity<List<AllowanceResponseDTO>> allowance(@RequestParam String email){
        List<AllowanceResponseDTO> response = allowanceQueryService.findByEmail(email);
        return ResponseEntity.ok(response);
    }

}
