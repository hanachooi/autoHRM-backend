package com.example.autoHRM_backend.domain.commute;

import com.example.autoHRM_backend.domain.employee.Employee;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("WORK")
@RequiredArgsConstructor
@Getter
public class WorkCommute extends Commute {

    private Long overtime;
    private Long nighttime;

    @Builder
    protected WorkCommute(LocalDateTime startTime, Employee employee, boolean status) {
        super(startTime, employee, status);
    }

    @Override
    public void setTime(Long overtime, Long nighttime, Long time){
        this.overtime = overtime;
        this.nighttime = nighttime;
        super.setTime(time);
    }

    @Override
    public Long getNightTime() {
        return nighttime;
    }

    @Override
    public Long getOverTime() {
        return overtime;
    }

}
