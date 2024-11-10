package com.example.autoHRM_backend.api.complaint;

import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.company.CompanyRepository;
import com.example.autoHRM_backend.domain.complaint.Complaint;
import com.example.autoHRM_backend.domain.complaint.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final CompanyRepository companyRepository;

    public void createComplaint(Company company, ComplaintRequestDTO complaintRequestDTO) {

        Complaint complaint = new Complaint(complaintRequestDTO.getEmail(), complaintRequestDTO.getTitle(),
                complaintRequestDTO.getDescription() , company);
        complaintRepository.save(complaint);

        company.plusCount();
        companyRepository.save(company);

    }
}
