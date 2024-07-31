package com.example.autoHRM_backend.domain.allowance;


import com.example.autoHRM_backend.api.allowance.dto.AllowanceResponseDTO;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AllowanceQueryRepository{

    private final JPAQueryFactory queryFactory;

    public List<AllowanceResponseDTO> findByEmployee(String email) {

        QAllowance qAllowance = QAllowance.allowance;

        List<Allowance> list = queryFactory.selectFrom(qAllowance)
                .join(qAllowance.commute)
                .join(qAllowance.commute.employee)
                .where(qAllowance.commute.employee.email.eq(email))
                .fetch();

        List<AllowanceResponseDTO> dtos = new ArrayList<>();

        for(Allowance allowance : list){
            dtos.add(new AllowanceResponseDTO(allowance.getTime(), allowance.getAllowancePay()));
        }
        return dtos;
    }


}
