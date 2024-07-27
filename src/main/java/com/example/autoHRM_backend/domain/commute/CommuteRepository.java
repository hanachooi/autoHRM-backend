package com.example.autoHRM_backend.domain.commute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommuteRepository extends JpaRepository<Commute, Long> {


    @Query("SELECT c FROM Commute c WHERE c.employee.email = :email " +
            "AND (DATE(c.startTime) = :today OR DATE(c.startTime) = :yesterday) " +
            "AND c.endTime IS NULL")
    List<Commute> findCommutes(@Param("email") String email,
                               @Param("today") LocalDate today,
                               @Param("yesterday") LocalDate yesterday);



}
