package com.example.autoHRM_backend.domain.allowance;

import com.example.autoHRM_backend.domain.commute.Commute;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class OverTimeAllowance extends Allowance {

    @Builder
    protected OverTimeAllowance(Commute commute, Long time){
        super(commute, time);
    }

}
