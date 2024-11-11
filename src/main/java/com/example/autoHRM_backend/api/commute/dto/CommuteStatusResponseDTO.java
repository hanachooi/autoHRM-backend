package com.example.autoHRM_backend.api.commute.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommuteStatusResponseDTO {

    private String name;
    private String email;
    private LocalDateTime startTime;
    private Boolean status;

}
