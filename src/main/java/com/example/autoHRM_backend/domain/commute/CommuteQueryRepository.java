package com.example.autoHRM_backend.domain.commute;

import com.example.autoHRM_backend.api.commute.dto.CommuteResponseDTO;
import com.example.autoHRM_backend.api.commute.dto.EmployeesCommuteDTO;
import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeQueryRepository;
import com.example.autoHRM_backend.domain.employee.QEmployee;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CommuteQueryRepository{

    private final JPAQueryFactory queryFactory;
    private final EmployeeQueryRepository employeeQueryRepository;

    QCommute qCommute = QCommute.commute;
    QEmployee qEmployee  = QEmployee.employee;

    public Commute checkInStatus(String email) {


        Commute commute = queryFactory.selectFrom(qCommute)
                .join(qCommute.employee)
                .where(qCommute.employee.email.eq(email))
                .orderBy(qCommute.startTime.desc()) // 날짜 필드에 따라 내림차순 정렬
                .fetchFirst(); // 가장 최근 기록 하나만 가져옴


        return commute;
    }

    public List<EmployeesCommuteDTO> findCompanyCommutes(Company company, String email) {

        List<Employee> employees = employeeQueryRepository.findAllEmployees(null, email, company);
        for(Employee e : employees){
            System.out.println(e.getEmail());
        }

        return employees.stream().map(employee -> {
            // 해당 사원의 출퇴근 기록 조회
            List<CommuteResponseDTO> commutes = queryFactory
                    .select(Projections.constructor(
                            CommuteResponseDTO.class,
                            qCommute.id,
                            qCommute.startTime,
                            qCommute.endTime
                    ))
                    .from(qCommute)
                    .join(qCommute.employee, qEmployee)
                    .where(qCommute.employee.eq(employee))
                    .fetch();

            // 출퇴근 기록이 없을 경우 빈 리스트로 처리
            if (commutes == null) {
                commutes = Collections.emptyList();
            }

            return new EmployeesCommuteDTO(employee.getEmail(), employee.getName(), commutes);
        }).collect(Collectors.toList());

    }

}
