package com.example.autoHRM_backend.api.calendar.controller;

import com.example.autoHRM_backend.api.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CalendarController {
    private final CalendarService googleCalendarService;

    @PostMapping("/calender")
    public ResponseEntity<Void> getHoliday() throws Exception{
        googleCalendarService.saveHolidays();

        return ResponseEntity.ok().build();
    }
}
