package com.example.autoHRM_backend.api.allowance.dto;

import com.example.autoHRM_backend.domain.commute.Commute;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class AllowanceCommuteResponseDTO {

    private String date;
    private String type;
    private List<AllowanceResponseDTO> allowances; // 관련 Allowance 목록

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

    public AllowanceCommuteResponseDTO(Commute commute, List<AllowanceResponseDTO> allowances) {
        this.date = commute.getStartTime().format(formatter);
        this.type = commute.getClass().getSimpleName().replace("Commute", "");
        this.allowances = allowances;
    }

}
