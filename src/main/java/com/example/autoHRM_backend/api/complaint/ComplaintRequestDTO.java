package com.example.autoHRM_backend.api.complaint;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComplaintRequestDTO {

    private String email;
    private String title;
    private String description;

    public ComplaintRequestDTO(String email, String title, String description) {
        this.email = email;
        this.title = title;
        this.description = description;
    }
}
