package com.example.autoHRM_backend.domain.allowance;


import com.example.autoHRM_backend.domain.commute.Commute;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor
public class NightAllowance extends Allowance {

    @Builder
    public NightAllowance(Commute commute, Long time){
        super(commute, time);
    }

}
