package com.example.autoHRM_backend.api.apply.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApplyRequestDTO {


    private String rectifyDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String content;
}
