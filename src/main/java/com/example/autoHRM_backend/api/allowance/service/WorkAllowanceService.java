package com.example.autoHRM_backend.api.allowance.service;

import com.example.autoHRM_backend.domain.commute.Commute;
import com.example.autoHRM_backend.domain.commute.CommuteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkAllowanceService implements AllowanceService {

    private final Long nightAddition = 50L;

    @Override
    public void createNightAllowance(Long nightTime) {



    }
}
