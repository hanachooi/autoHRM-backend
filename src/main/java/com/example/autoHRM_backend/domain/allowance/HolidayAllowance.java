package com.example.autoHRM_backend.domain.allowance;

import com.example.autoHRM_backend.domain.commute.Commute;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("HOLIDAY")
@NoArgsConstructor
public class HolidayAllowance extends Allowance {

    @Builder
    protected HolidayAllowance(Commute commute, Long time, Long allowancePay){
        super(commute, time, allowancePay);
    }

}
