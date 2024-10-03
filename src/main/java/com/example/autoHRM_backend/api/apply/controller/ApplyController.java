package com.example.autoHRM_backend.api.apply.controller;

import com.example.autoHRM_backend.api.apply.dto.ApplyRectifyRequestDTO;
import com.example.autoHRM_backend.api.apply.service.ApplyService;
import com.example.autoHRM_backend.auth.service.EmployeeUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;


    @PostMapping("/apply/commute")
    public ResponseEntity<Void> createReviseApply(@RequestBody ApplyRectifyRequestDTO request) throws Exception{
        String employeeLoginId = getEmployeeLoginId();
        System.out.println(employeeLoginId);
        applyService.createReviseApply(employeeLoginId, request);


        return ResponseEntity.ok().build();
    }



    private String getEmployeeLoginId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails)principal;

        return employeeUserDetails.getEmail();
    }

}
