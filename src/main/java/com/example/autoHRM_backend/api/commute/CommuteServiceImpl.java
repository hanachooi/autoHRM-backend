package com.example.autoHRM_backend.api.commute;

import com.example.autoHRM_backend.domain.commute.Commute;
import com.example.autoHRM_backend.domain.commute.CommuteRepository;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommuteServiceImpl implements CommuteService {

    private final EmployeeRepository employeeRepository;
    private final CommuteRepository commuteRepository;

    @Override
    public void checkIn(String email){

        Employee employee = employeeRepository.findByEmail(email);

        Commute commute = Commute.builder()
                .startTime(LocalDateTime.now())
                .employee(employee)
                .build();
        commuteRepository.save(commute);
    }

    @Override
    public void checkOut(String email) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        List<Commute> commutes = commuteRepository.findCommutes(email, today, yesterday);
        Commute commute = commutes.get(0);
        commute.checkOut();
        commuteRepository.save(commute);
    }

}
