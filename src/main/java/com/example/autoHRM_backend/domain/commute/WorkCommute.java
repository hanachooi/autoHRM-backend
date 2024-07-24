package com.example.autoHRM_backend.domain.commute;

import com.example.autoHRM_backend.domain.employee.Employee;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("WORK")
@RequiredArgsConstructor
public class WorkCommute extends Commute {

    @Builder
    public WorkCommute(LocalDateTime startTime, Employee employee){
        super(startTime, employee);
    }

}
