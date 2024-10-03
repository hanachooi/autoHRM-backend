package com.example.autoHRM_backend.api.calendar.controller;

import com.example.autoHRM_backend.api.calendar.dto.ScheduleRequestDTO;
import com.example.autoHRM_backend.api.calendar.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/")
    public ResponseEntity<Void> createSchedule(@RequestBody ScheduleRequestDTO scheduleRequestDTO){
        scheduleService.createSchedule(scheduleRequestDTO);
        return ResponseEntity.ok().build();
    }

}
