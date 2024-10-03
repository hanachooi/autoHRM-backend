package com.example.autoHRM_backend.domain.allowance;

import com.example.autoHRM_backend.domain.commute.Commute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllowanceRepository extends JpaRepository<Allowance, Long> {

    List<Allowance> findByCommute(Commute commute);

}
