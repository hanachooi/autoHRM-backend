package com.example.autoHRM_backend.domain.company;

import com.example.autoHRM_backend.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {


    List<Department> findByCompany(Company company);

}
