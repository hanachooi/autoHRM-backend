package com.example.autoHRM_backend.api.allowance.service;

import com.example.autoHRM_backend.api.allowance.dto.AllowanceCommuteResponseDTO;
import com.example.autoHRM_backend.api.allowance.dto.AllowanceResponseDTO;
import com.example.autoHRM_backend.domain.allowance.Allowance;
import com.example.autoHRM_backend.domain.allowance.AllowanceQueryRepository;
import com.example.autoHRM_backend.domain.allowance.AllowanceRepository;
import com.example.autoHRM_backend.domain.commute.Commute;
import com.example.autoHRM_backend.domain.commute.CommuteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllowanceQueryService {

    //private final AllowanceQueryRepository allowanceQueryRepository;
    private final AllowanceRepository allowanceRepository;
    private final CommuteRepository commuteRepository;

    public List<AllowanceCommuteResponseDTO> findMonthlyAllowance(String employeeLoginId, LocalDateTime start, LocalDateTime end) {

        List<Commute> commutes = commuteRepository.findCommutesBetween(start, end, employeeLoginId);

        Map<Commute, List<Allowance>> commuteAllowancesMap = new HashMap<>();

        for (Commute commute : commutes) {
            List<Allowance> allowances = allowanceRepository.findByCommute(commute);
            commuteAllowancesMap.put(commute, allowances);
        }

        List<AllowanceCommuteResponseDTO> response = new ArrayList<>();
        for (Map.Entry<Commute, List<Allowance>> entry : commuteAllowancesMap.entrySet()) {
            Commute commute = entry.getKey();
            List<Allowance> allowances = entry.getValue();

            List<AllowanceResponseDTO> allowanceResponseList = allowances.stream().map(allowance -> {
                AllowanceResponseDTO allowanceDto = new AllowanceResponseDTO(allowance.getClass().getSimpleName(), allowance.getTime(), allowance.getAllowancePay());
                return allowanceDto;
            }).collect(Collectors.toList());

            if (!allowanceResponseList.isEmpty()) {
                AllowanceCommuteResponseDTO dto = new AllowanceCommuteResponseDTO(commute, allowanceResponseList);
                response.add(dto);
            }
        }
        return response;
    }

}
