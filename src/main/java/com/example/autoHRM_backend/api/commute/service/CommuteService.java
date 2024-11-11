package com.example.autoHRM_backend.api.commute.service;

import com.example.autoHRM_backend.api.commute.dto.CommuteResponseDTO;
import com.example.autoHRM_backend.api.commute.dto.CommuteStatusResponseDTO;
import com.example.autoHRM_backend.api.commute.dto.EmployeesCommuteDTO;
import com.example.autoHRM_backend.domain.company.Company;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface CommuteService {

    void checkIn(String email);

    void checkOut(String email);

    CommuteStatusResponseDTO checkInStatus(String email);

    List<CommuteResponseDTO> findCommute(String employeeLoginId, String filterType, LocalDateTime startDate);

    List<EmployeesCommuteDTO> findCompanyCommutes(Company company, String email);
}
