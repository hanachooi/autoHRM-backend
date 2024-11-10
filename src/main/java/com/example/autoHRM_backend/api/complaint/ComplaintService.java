package com.example.autoHRM_backend.api.complaint;

import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.company.CompanyRepository;
import com.example.autoHRM_backend.domain.complaint.Complaint;
import com.example.autoHRM_backend.domain.complaint.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<ComplaintResponseDTO> findMyComplaints(Company company) {

        List<Complaint> complaints = complaintRepository.findByCompany(company);
        List<ComplaintResponseDTO> complaintResponseDTOs = new ArrayList<>();

        for(Complaint complaint : complaints) {
            complaintResponseDTOs.add(new ComplaintResponseDTO(complaint.getId(), complaint.getEmail(), complaint.getDescription(), complaint.getTitle()));
        }

        return complaintResponseDTOs;
    }

    public Long findMyComplaintCount(Company company) {

        return company.getComplaintCount();

    }
}
