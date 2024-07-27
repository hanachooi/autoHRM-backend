package com.example.autoHRM_backend.api.allowance.service;

import com.example.autoHRM_backend.domain.allowance.Allowance;
import com.example.autoHRM_backend.domain.allowance.NightAllowance;
import com.example.autoHRM_backend.domain.commute.Commute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllowanceServiceImpl implements AllowanceService {

    private final Long nightAddition = 50L;

    @Override
    public void createNightAllowance(Commute commute) {

        Long time = commute.getNightTime();


        /*Allowance allowance = NightAllowance.builder()
                .commute(commute)
                .time(time)
                .*/

    }
/*
    public Long calculateNightAllowance(Long time) {



    }
 */
}
