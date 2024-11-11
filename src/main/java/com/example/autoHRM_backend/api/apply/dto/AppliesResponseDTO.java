package com.example.autoHRM_backend.api.apply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class AppliesResponseDTO {

    private Long id;

    private String name;

    private String email;

    private LocalDateTime rectifyDate;

    private LocalDateTime applyDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String type;

    private String content;

    private String status;

}
