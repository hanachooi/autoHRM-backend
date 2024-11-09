package com.example.autoHRM_backend.api.apply.service;

import com.example.autoHRM_backend.api.apply.dto.AppliesResponseDTO;
import com.example.autoHRM_backend.api.apply.dto.ApplyRequestDTO;
import com.example.autoHRM_backend.domain.apply.Apply;
import com.example.autoHRM_backend.domain.apply.ApplyRepository;
import com.example.autoHRM_backend.domain.company.Company;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeQueryRepository;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeQueryRepository employeeQueryRepository;

    public Apply createReviseApply(String employeeLoginId, ApplyRequestDTO request){


        Employee employee = employeeRepository.findByEmail(employeeLoginId);

        Apply apply = new Apply(
                LocalDateTime.parse(request.getRectifyDate() + "T00:00:00"), // rectifyDate는 String이라 LocalDateTime으로 변환
                LocalDateTime.now(), // applyDate는 현재 시각
                request.getStartTime(),
                request.getEndTime(),
                "Commute", // type 필드는 'Commute'로
                employee,
                request.getContent(),
                "미처리"
        );

        // Apply 저장
        return applyRepository.save(apply);
    }

    public Apply createFieldApply(String employeeLoginId, ApplyRequestDTO request){


        Employee employee = employeeRepository.findByEmail(employeeLoginId);

        Apply apply = new Apply(
                LocalDateTime.parse(request.getRectifyDate() + "T00:00:00"), // rectifyDate는 String이라 LocalDateTime으로 변환
                LocalDateTime.now(), // applyDate는 현재 시각
                request.getStartTime(),
                request.getEndTime(),
                "FieldWork", // type 필드는 'Commute'로
                employee,
                request.getContent(),
                "미처리"
        );

        // Apply 저장
        return applyRepository.save(apply);
    }

    public Apply createHolidayApply(String employeeLoginId, ApplyRequestDTO request){


        Employee employee = employeeRepository.findByEmail(employeeLoginId);

        Apply apply = new Apply(
                LocalDateTime.parse(request.getRectifyDate() + "T00:00:00"), // rectifyDate는 String이라 LocalDateTime으로 변환
                LocalDateTime.now(), // applyDate는 현재 시각
                request.getStartTime(),
                request.getEndTime(),
                "Holiday", // type 필드는 'Commute'로
                employee,
                request.getContent(),
                "미처리"
        );

        // Apply 저장
        return applyRepository.save(apply);
    }


    public List<AppliesResponseDTO> findAllApply(Company company, String type, String status) {

        List<Employee> myEmployees = employeeQueryRepository.findAllEmployees(null, null, company);
        List<Apply> applies = applyRepository.findByEmployeeInAndOptionalTypeAndStatus(myEmployees, type, status);

        for(Apply a : applies){
            System.out.println(a.getEmployee().getEmail());
        }


        // AppliesResponseDTO로 변환하여 반환
        return applies.stream()
                .map(apply -> new AppliesResponseDTO(
                        apply.getId(),
                        apply.getEmployee().getName(),
                        apply.getEmployee().getEmail(),
                        apply.getRectifyDate(),
                        apply.getApplyDate(),
                        apply.getStartTime(),
                        apply.getEndTime(),
                        apply.getType(),
                        apply.getContent(),
                        apply.getStatus()))
                .collect(Collectors.toList());

    }
}
