package com.example.autoHRM_backend.domain.allowance;

import com.example.autoHRM_backend.domain.commute.Commute;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("OVERTIME")
public class OverTimeAllowance extends Allowance {

    @Builder
    protected OverTimeAllowance(Commute commute, Long time, Long allowancePay){
        super(commute, time, allowancePay);
    }

}
