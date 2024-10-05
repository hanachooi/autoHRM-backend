package com.example.autoHRM_backend.domain.company;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    private Long id;

    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String ownerName;
    private String registrationNumber;

    @OneToMany(mappedBy = "company")
    private List<Department> departments;

}
