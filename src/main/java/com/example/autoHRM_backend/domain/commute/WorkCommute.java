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
    private Long time;

    @Builder
    public WorkCommute(LocalDateTime startTime, Employee employee){
        super(startTime, employee);
    }

    public void setTime(Long overtime, Long nighttime, Long time){
        this.overtime = overtime;
        this.nighttime = nighttime;
        this.time = time;
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
