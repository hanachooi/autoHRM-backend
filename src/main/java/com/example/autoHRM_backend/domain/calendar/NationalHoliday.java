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
public class NationalHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nationalHolidayName;
    private LocalDate nationalHolidayDate;

    public NationalHoliday(String nationalHolidayName, LocalDate nationalHolidayDate) {
        this.nationalHolidayName = nationalHolidayName;
        this.nationalHolidayDate = nationalHolidayDate;
    }

}

