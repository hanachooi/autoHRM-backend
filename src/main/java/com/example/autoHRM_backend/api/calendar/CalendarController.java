package com.example.autoHRM_backend.api.calendar;

import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService googleCalendarService;

    @PostMapping("/calender")
    public ResponseEntity<Void> getHoliday() throws Exception{
        googleCalendarService.saveHolidays();

        return ResponseEntity.ok().build();
    }
}
