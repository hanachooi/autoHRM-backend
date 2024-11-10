package com.example.autoHRM_backend.api.apply.controller;

import com.example.autoHRM_backend.api.apply.dto.AppliesResponseDTO;
import com.example.autoHRM_backend.api.apply.dto.ApplyRequestDTO;
import com.example.autoHRM_backend.api.apply.service.ApplyService;
import com.example.autoHRM_backend.auth.service.AuthUtil;
import com.example.autoHRM_backend.auth.service.EmployeeUserDetails;
import com.example.autoHRM_backend.domain.company.Company;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;
    private final AuthUtil authUtil;


    @PostMapping("/apply/commute")
    public ResponseEntity<Void> createReviseApply(@RequestBody ApplyRequestDTO request) throws Exception{
        String employeeLoginId = authUtil.getEmployeeLoginId();
        System.out.println(employeeLoginId);
        applyService.createReviseApply(employeeLoginId, request);


        return ResponseEntity.ok().build();
    }

    @PostMapping("/apply/fieldwork")
    public ResponseEntity<Void> createfieldApply(@RequestBody ApplyRequestDTO request) throws Exception{
        String employeeLoginId = authUtil.getEmployeeLoginId();
        System.out.println(employeeLoginId);
        applyService.createFieldApply(employeeLoginId, request);


        return ResponseEntity.ok().build();
    }

    @PostMapping("/apply/holiday")
    public ResponseEntity<Void> createholidayApply(@RequestBody ApplyRequestDTO request) throws Exception{
        String employeeLoginId = authUtil.getEmployeeLoginId();
        System.out.println(employeeLoginId);
        applyService.createHolidayApply(employeeLoginId, request);


        return ResponseEntity.ok().build();
    }

    @GetMapping("/applies/my")
    public ResponseEntity<List<AppliesResponseDTO>> findAllApply(@RequestParam(required = false) String type, @RequestParam(required = false) String status) throws Exception{

        Company company = authUtil.getMyCompany();
        List<AppliesResponseDTO> dtos = applyService.findAllApply(company, type, status);


        return ResponseEntity.ok(dtos);
    }

    //
    @PatchMapping("/apply/holiday")
    public ResponseEntity<Void> updateApply(@RequestParam Long id, @RequestParam String type, @RequestParam String status){

        // 일단은 type구별 없이 서비스 호출 -> 지원 상태만 변경
        applyService.updateApply(id, status);
        return ResponseEntity.ok().build();
    }

}
