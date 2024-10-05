package com.example.autoHRM_backend.api.company;

import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.company.CompanyRepository;
import com.example.autoHRM_backend.domain.company.Department;
import com.example.autoHRM_backend.domain.company.DepartmentRepository;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    public List<Department> findMyDepartments(String loginEmployeeId) {

        Employee employee = employeeRepository.findByEmail(loginEmployeeId);
        Company company = companyRepository.findCompanyByEmployeeId(employee.getId());

        List<Department> departments = departmentRepository.findByCompany(company);
        return departments;
    }

}
