package com.example.autoHRM_backend.domain.allowance;


import com.example.autoHRM_backend.domain.commute.Commute;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("NIGHT")
@NoArgsConstructor
public class NightAllowance extends Allowance {

    @Builder
    protected NightAllowance(Commute commute, Long time, Long allowancePay){
        super(commute, time, allowancePay);
    }

}
