package com.example.autoHRM_backend.domain.calendar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String holidayName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Holiday(String summary, LocalDateTime startDate, LocalDateTime endDate) {
        this.holidayName = summary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}

