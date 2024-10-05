package com.example.autoHRM_backend.api.company;

import com.example.autoHRM_backend.auth.service.EmployeeUserDetails;
import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.company.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/department/my")
    public ResponseEntity<List<Department>> findMyDepartment() {

        String employeeLoginId = getEmployeeLoginId();

        List<Department> departments = companyService.findMyDepartments(employeeLoginId);

        return ResponseEntity.ok(departments);
    }

    private String getEmployeeLoginId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails)principal;

        return employeeUserDetails.getEmail();
    }

}
