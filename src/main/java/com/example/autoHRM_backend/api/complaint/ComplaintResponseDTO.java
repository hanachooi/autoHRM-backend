package com.example.autoHRM_backend.api.complaint;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComplaintResponseDTO {


    private Long id;
    private String email;
    private String title;
    private String description;

    public ComplaintResponseDTO(Long id, String email, String description, String title) {
        this.id = id;
        this.email = email;
        this.description = description;
        this.title = title;
    }
}
