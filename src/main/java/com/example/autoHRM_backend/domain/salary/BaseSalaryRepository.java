package com.example.autoHRM_backend.domain.salary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseSalaryRepository extends JpaRepository<BaseSalary, Long> {

}
