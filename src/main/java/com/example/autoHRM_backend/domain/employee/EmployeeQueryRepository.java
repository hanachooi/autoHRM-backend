package com.example.autoHRM_backend.domain.employee;

import com.example.autoHRM_backend.api.employee.dto.EmployeeDetailResponseDTO;
import com.example.autoHRM_backend.api.employee.dto.EmployeesResponseDTO;
import com.example.autoHRM_backend.api.salary.dto.SalariesResponseDTO;
import com.example.autoHRM_backend.api.salary.dto.SalaryDTO;
import com.example.autoHRM_backend.domain.calendar.DayOfWeek;
import com.example.autoHRM_backend.domain.calendar.QWeeklySchedule;
import com.example.autoHRM_backend.domain.calendar.ScheduleType;
import com.example.autoHRM_backend.domain.calendar.WeeklySchedule;
import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.company.QCompany;
import com.example.autoHRM_backend.domain.company.QDepartment;
import com.example.autoHRM_backend.domain.salary.BaseSalary;
import com.example.autoHRM_backend.domain.salary.QBaseSalary;
import com.example.autoHRM_backend.domain.salary.QSalary;
import com.example.autoHRM_backend.domain.salary.Salary;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EmployeeQueryRepository{

    private final JPAQueryFactory queryFactory;
    QEmployee qEmployee = QEmployee.employee;
    QSalary qSalary = QSalary.salary1;
    QWeeklySchedule qWeeklySchedule = QWeeklySchedule.weeklySchedule;
    QBaseSalary qBaseSalary = QBaseSalary.baseSalary1;
    QCompany qCompany = QCompany.company;
    QDepartment qDepartment = QDepartment.department;

    // 사내 사원 찾기
    public List<Employee> findAllEmployees(Long departmentId, String employeeId, Company company) {

        // 조건 담는 빌더
        BooleanBuilder builder = new BooleanBuilder();

        // 조건 필터링 하기
        if(employeeId != null && departmentId != null) {
            builder.and(qEmployee.email.eq(employeeId)
                    .and(qEmployee.department.id.eq(departmentId)));
        }else if(employeeId == null && departmentId != null) {
            builder.and(qEmployee.department.id.eq(departmentId));
        }else if(employeeId != null && departmentId == null) {
            builder.and(qEmployee.email.eq(employeeId));
        }
        builder.and(qEmployee.department.company.eq(company));

        return queryFactory
                .select(qEmployee)
                .from(qEmployee)
                .join(qEmployee.department, qDepartment) // Employee와 Department 조인
                .join(qDepartment.company, qCompany) // Department와 Company 조인
                .where(builder)
                .orderBy(qEmployee.department.id.asc())
                .fetch();

    }

    public EmployeeDetailResponseDTO findEmployeeDetail(String employeeEmail){

        System.out.println("EmployeeQueryRepository.findEmployeeDetail");
        System.out.println(employeeEmail);

        // 주 시간표 추출 및 Map<DayOfWeek, ScheduleType> 변환
        Map<DayOfWeek, ScheduleType> weekSchedule = queryFactory
                .selectFrom(qWeeklySchedule)
                .where(qWeeklySchedule.employee.email.eq(employeeEmail))
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        WeeklySchedule::getDayOfWeek,   // Key: DayOfWeek
                        WeeklySchedule::getScheduleType // Value: ScheduleType
                ));

        // 최근 급여 정보 가져오기
        Salary latestSalary = queryFactory
                .selectFrom(qSalary)
                .where(qSalary.employee.email.eq(employeeEmail))
                .orderBy(qSalary.year.desc(), qSalary.month.desc()) // year, month 순으로 내림차순 정렬
                .fetchFirst(); // 가장 첫 번째 결과만 가져옴

        // 기본급 정보 가져오기
        BaseSalary baseSalary = queryFactory
                .selectFrom(qBaseSalary)
                .where(qBaseSalary.employee.email.eq(employeeEmail))
                .fetchOne();

        // null 값이 아닐 수 밖에 없는 칼럼들을 먼저 추가 후 나중에 개별 추가
        EmployeeDetailResponseDTO employeeDetailResponseDTO = queryFactory
                .select(Projections.constructor(EmployeeDetailResponseDTO.class,
                        qEmployee.name,
                        qEmployee.email,
                        qEmployee.companyName,
                        qEmployee.department.departmentName))
                .from(qEmployee)
                .join(qEmployee.department)
                .join(qEmployee.department.company)
                .where(qEmployee.email.eq(employeeEmail))
                .fetchOne();

        System.out.println(employeeDetailResponseDTO);

        // 주간 일정과 급여 정보 설정
        if (employeeDetailResponseDTO != null) {
            employeeDetailResponseDTO.setWeeklySchedule(weekSchedule.isEmpty() ? null : weekSchedule);  // 빈 Map 처리
            employeeDetailResponseDTO.setSalary(latestSalary == null ? null : latestSalary.getSalary());
            employeeDetailResponseDTO.setBaseSalary(baseSalary == null ? null : baseSalary.getBaseSalary());
            employeeDetailResponseDTO.setYear(baseSalary == null ? null : baseSalary.getYear());
            employeeDetailResponseDTO.setWage(baseSalary == null ? null : baseSalary.getWage());
            employeeDetailResponseDTO.setMinuteWage(baseSalary == null ? null : baseSalary.getMinuteWage());

        }

        return employeeDetailResponseDTO;

    }

    public List<SalariesResponseDTO> findSalaries(Company company, String email, Boolean status) {

        BooleanBuilder builder = new BooleanBuilder();

        System.out.println("EmployeeQueryRepository.findSalaries");
        System.out.println(company.getDepartments());

        List<Employee> employees = findAllEmployees(null, email, company);
        for(Employee employee : employees) {
            System.out.println(employee.getEmail());
        }

        List<Long> employeeIds = employees.stream()
                .map(Employee::getId)
                .toList();


        if (!employeeIds.isEmpty()) {
            builder.and(qEmployee.id.in(employeeIds));
            System.out.println(employeeIds);
        } else {
            return List.of();
        }

        List<SalariesResponseDTO> salariesResponseDTOs = employees.stream()
                .map(employee -> {
                    List<SalaryDTO> salaryDTOs = employee.getSalary().stream()
                            .map(salary -> new SalaryDTO(
                                    salary.getYear(),
                                    salary.getMonth(),
                                    salary.getSalary(),
                                    salary.getUnpaid(),
                                    salary.getStatus()
                            ))
                            .collect(Collectors.toList());
                    return new SalariesResponseDTO(employee.getName(), employee.getEmail(), salaryDTOs);
                })
                .collect(Collectors.toList());

        return salariesResponseDTOs;

    }
}
