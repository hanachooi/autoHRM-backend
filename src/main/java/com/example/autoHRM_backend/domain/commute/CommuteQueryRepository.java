package com.example.autoHRM_backend.domain.commute;

import com.example.autoHRM_backend.api.allowance.dto.AllowanceResponseDTO;
import com.example.autoHRM_backend.domain.allowance.Allowance;
import com.example.autoHRM_backend.domain.allowance.QAllowance;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommuteQueryRepository{

    private final JPAQueryFactory queryFactory;

    public Commute checkInStatus(String email) {

        QCommute qCommute = QCommute.commute;

        Commute commute = queryFactory.selectFrom(qCommute)
                .join(qCommute.employee)
                .where(qCommute.employee.email.eq(email))
                .orderBy(qCommute.startTime.desc()) // 날짜 필드에 따라 내림차순 정렬
                .fetchOne(); // 가장 최근 기록 하나만 가져옴

        return commute;
    }


}
