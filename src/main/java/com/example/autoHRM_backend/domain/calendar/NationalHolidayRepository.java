package com.example.autoHRM_backend.domain.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationalHolidayRepository extends JpaRepository<NationalHoliday, Long> {

}
