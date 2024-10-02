package com.example.autoHRM_backend.api.commute.service;

import com.example.autoHRM_backend.api.commute.dto.CommuteRequestDTO;
import com.example.autoHRM_backend.api.commute.dto.CommuteResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface CommuteService {

    void checkIn(String email);

    void checkOut(String email);

    boolean checkInStatus(String email);

    List<CommuteResponseDTO> findCommute(String employeeLoginId, String filterType, LocalDateTime startDate);
}
