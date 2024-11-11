package com.example.autoHRM_backend.domain.company;

import com.example.autoHRM_backend.domain.complaint.Complaint;
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
    private Long complaintCount;

    @OneToMany(mappedBy = "company")
    private List<Department> departments;

    @OneToMany(mappedBy = "company")
    private List<Complaint> complaints;

    public void plusCount(){
        complaintCount++;
    }

}
