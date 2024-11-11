package com.example.autoHRM_backend.api.company;

import com.example.autoHRM_backend.auth.service.AuthUtil;
import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.company.CompanyRepository;
import com.example.autoHRM_backend.domain.company.Department;
import com.example.autoHRM_backend.domain.company.DepartmentRepository;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;


    public List<DepartmentResponseDTO> findMyDepartments(String employeeLoginId) {

        System.out.println("CompanyService.findMyDepartments");

        Company company = companyRepository.findCompanyByEmployeeEmail(employeeLoginId);

        System.out.println(company.getCompanyName());

        List<DepartmentResponseDTO> dtos = new ArrayList<>();

        List<Department> departments = departmentRepository.findByCompany(company);
        for(Department department : departments) {
            if(department.getDepartmentName().equals("회사")){
                continue;
            }
            DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO(department.getId(), department.getDepartmentName());
            dtos.add(departmentResponseDTO);
        }

        return dtos;
    }

}
