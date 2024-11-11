package com.example.autoHRM_backend.domain.complaint;

import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.company.Department;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "complaint_id")
    private Long id;

    private String email;
    private String title;
    private String description;

    public Complaint(String email, String title, String description, Company company) {
        this.email = email;
        this.title = title;
        this.description = description;
        this.company = company;
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
