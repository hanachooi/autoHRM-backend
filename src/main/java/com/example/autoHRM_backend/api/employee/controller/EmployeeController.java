package com.example.autoHRM_backend.api.employee.controller;

import com.example.autoHRM_backend.api.employee.dto.EmployeeDetailResponseDTO;
import com.example.autoHRM_backend.api.employee.dto.EmployeesResponseDTO;
import com.example.autoHRM_backend.api.employee.service.EmployeeService;
import com.example.autoHRM_backend.auth.service.AuthUtil;
import com.example.autoHRM_backend.domain.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthUtil authUtil;

    @GetMapping("/employees/my")
    public ResponseEntity<List<EmployeesResponseDTO>> findMyEmployees(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String employeeEmail){

        String employeeId = authUtil.getEmployeeLoginId();
        List<EmployeesResponseDTO> employees = employeeService.findMyEmployees(departmentId, employeeEmail, employeeId);

        return ResponseEntity.ok(employees);
    }

    // employeeEmail 로 request 받기
    @GetMapping("/employee")
    public ResponseEntity<EmployeeDetailResponseDTO> findEmployeeDetail(@RequestParam String employeeEmail){

        EmployeeDetailResponseDTO employeeDetailResponseDTO = employeeService.findEmployeeDetail(employeeEmail);
        return ResponseEntity.ok(employeeDetailResponseDTO);
    }

}
