package com.example.autoHRM_backend.auth.service;

import com.example.autoHRM_backend.auth.dto.CompanyDTO;
import com.example.autoHRM_backend.auth.dto.JoinDTO;
import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.company.CompanyRepository;
import com.example.autoHRM_backend.domain.company.DepartmentRepository;
import com.example.autoHRM_backend.domain.company.Department;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;

    public JoinService(EmployeeRepository employeeRepository, BCryptPasswordEncoder bCryptPasswordEncoder
    , CompanyRepository companyRepository, DepartmentRepository departmentRepository) {

        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String name = joinDTO.getName();
        String email = joinDTO.getEmail();
        String password = joinDTO.getPassword();
        Long departmentId = joinDTO.getDepartmentId();
        String role = joinDTO.getRole();
        Department department = departmentRepository.findById(departmentId).orElse(null);

        Employee data = new Employee();

        data.setName(name);
        data.setEmail(email);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole(role);
        data.setDepartment(department);

        employeeRepository.save(data);
    }

    public void createCompany(CompanyDTO companyDTO) {
        Company company = Company.builder()
                .companyName(companyDTO.getCompanyName())
                .companyAddress(companyDTO.getCompanyAddress())
                .companyPhone(companyDTO.getCompanyPhone())
                .ownerName(companyDTO.getOwnerName())
                .registrationNumber(companyDTO.getRegistrationNumber())
                .build();
        companyRepository.save(company);

        for(String departmentName : companyDTO.getDepartments()) {
            departmentRepository.save(new Department(departmentName, company));
        }

        JoinDTO joinDTO = new JoinDTO(companyDTO.getOwner_email(), companyDTO.getOwnerName(), companyDTO.getPassword(), "ROLE_ADMIN", 0L, company.getCompanyName());
        joinProcess(joinDTO);
    }

}
