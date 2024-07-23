package com.example.autoHRM_backend.domain.salary;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BaseSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long baseSalary;

}
