package com.example.autoHRM_backend.domain.salary;

import com.example.autoHRM_backend.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {


    Salary findByEmployeeAndYearAndMonth(Employee employee, int year, int month);

}
