package com.example.autoHRM_backend.api.apply.service;

import com.example.autoHRM_backend.api.apply.dto.ApplyRequestDTO;
import com.example.autoHRM_backend.domain.apply.Apply;
import com.example.autoHRM_backend.domain.apply.ApplyRepository;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final EmployeeRepository employeeRepository;

    public Apply createReviseApply(String employeeLoginId, ApplyRequestDTO request){


        Employee employee = employeeRepository.findByEmail(employeeLoginId);

        Apply apply = new Apply(
                LocalDateTime.parse(request.getRectifyDate() + "T00:00:00"), // rectifyDate는 String이라 LocalDateTime으로 변환
                LocalDateTime.now(), // applyDate는 현재 시각
                request.getStartTime(),
                request.getEndTime(),
                "Commute", // type 필드는 'Commute'로
                employee,
                request.getContent()
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
                request.getContent()
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
                request.getContent()
        );

        // Apply 저장
        return applyRepository.save(apply);
    }


}
