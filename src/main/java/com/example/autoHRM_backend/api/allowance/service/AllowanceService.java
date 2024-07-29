package com.example.autoHRM_backend.api.allowance.service;

import com.example.autoHRM_backend.domain.commute.Commute;
import org.springframework.stereotype.Service;

@Service
public interface AllowanceService {

    void createNightAllowance(Commute commute);

    void createOverAllowance(Commute commute);

}
