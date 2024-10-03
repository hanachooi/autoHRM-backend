package com.example.autoHRM_backend.domain.commute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface CommuteBatchRepository extends JpaRepository<Commute, Long> {

    // 추가 근무 수당을 위해서 저번주 데이터 찾아내는 repository 코드
    // 추가 근무 수당을 위해서 저번주 데이터 찾아내는 repository 코드
//    List<Commute> findByStartTimeGreaterThanEqualAndEndTimeLessThan(
//            LocalDateTime start,
//            LocalDateTime end,
//            Pageable pageable
//    );

    @Query("SELECT c FROM Commute c WHERE c.startTime >= :startTime AND c.endTime < :endTime")
    Page<Commute> findCommutesLastWeek(
            LocalDateTime startTime,
            LocalDateTime endTime,
            Pageable pageable
    );

}
