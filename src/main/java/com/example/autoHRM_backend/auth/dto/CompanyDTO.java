package com.example.autoHRM_backend.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CompanyDTO {

    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String ownerName;
    private String registrationNumber;
    private String owner_email;
    private String password;
    private List<String> departments;

    public CompanyDTO(String companyName, String companyAddress, String companyPhone, String ownerName, String registrationNumber, String owner_email, String password, List<String> departments) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyPhone = companyPhone;
        this.ownerName = ownerName;
        this.registrationNumber = registrationNumber;
        this.owner_email = owner_email;
        this.password = password;
        this.departments = departments;
    }

}
