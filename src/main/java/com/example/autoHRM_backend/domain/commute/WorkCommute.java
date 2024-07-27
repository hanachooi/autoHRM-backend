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
    public WorkCommute(LocalDateTime startTime, Employee employee){
        super(startTime, employee);
    }

    @Override
    public void setTime(Long overtime, Long nighttime){
        this.overtime = overtime;
        this.nighttime = nighttime;
    }

    @Override
    public Long getNightTime() {
        return nighttime;
    }

}
