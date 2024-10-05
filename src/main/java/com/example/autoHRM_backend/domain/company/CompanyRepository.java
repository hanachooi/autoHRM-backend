package com.example.autoHRM_backend.domain.company;

import com.example.autoHRM_backend.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT e.department.company FROM Employee e WHERE e.id = :employeeId")
    Company findCompanyByEmployeeId(@Param("employeeId") Long employeeId);
}
