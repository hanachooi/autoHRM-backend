package com.example.autoHRM_backend.api.company;

import com.example.autoHRM_backend.auth.service.AuthUtil;
import com.example.autoHRM_backend.auth.service.EmployeeUserDetails;
import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.company.Department;
import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
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
    private final AuthUtil authUtil;

    @GetMapping("/department/my")
    public ResponseEntity<List<DepartmentResponseDTO>> findMyDepartment() {

        String employeeId = authUtil.getEmployeeLoginId();
        System.out.println("CompanyController.findMyDepartment");

        List<DepartmentResponseDTO> departments = companyService.findMyDepartments(employeeId);

        return ResponseEntity.ok(departments);
    }

}
