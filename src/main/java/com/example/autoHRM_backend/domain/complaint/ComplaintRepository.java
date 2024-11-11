package com.example.autoHRM_backend.domain.complaint;

import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByCompany(Company company);

}
