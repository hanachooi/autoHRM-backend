package com.example.autoHRM_backend.api.allowance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllowanceResponseDTO {

    private Long time;

    private Long allowancePay;

    public AllowanceResponseDTO(Long time, Long allowancePay) {
        this.time = time;
        this.allowancePay = allowancePay;
    }

}
