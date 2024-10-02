package com.example.autoHRM_backend.api.commute.dto;

import com.example.autoHRM_backend.domain.commute.Commute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommuteResponseDTO {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public CommuteResponseDTO(Commute commute) {
        this.id = commute.getId();
        this.startTime = commute.getStartTime();
        this.endTime = commute.getEndTime();
    }

}
