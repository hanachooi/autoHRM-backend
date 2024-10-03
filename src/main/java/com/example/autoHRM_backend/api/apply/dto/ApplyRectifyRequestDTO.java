package com.example.autoHRM_backend.api.apply.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApplyRectifyRequestDTO {


    private String rectifyDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String content;
}
