package com.example.autoHRM_backend.api.allowance.service;

import com.example.autoHRM_backend.api.allowance.dto.AllowanceResponseDTO;
import com.example.autoHRM_backend.domain.allowance.Allowance;
import com.example.autoHRM_backend.domain.allowance.AllowanceQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllowanceQueryService {

    private final AllowanceQueryRepository allowanceQueryRepository;

    public List<AllowanceResponseDTO> findByEmail(String email) {
        return allowanceQueryRepository.findByEmployee(email);
    }

}
