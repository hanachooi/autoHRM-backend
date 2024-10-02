package com.example.autoHRM_backend.api.allowance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllowanceResponseDTO {

    private String type;

    private Long time;

    private Long allowancePay;

    public AllowanceResponseDTO(String type, Long time, Long allowancePay) {
        this.type = type.replace("Allowance", "");;
        this.time = time;
        this.allowancePay = allowancePay;
    }

}
