package com.example.autoHRM_backend.auth.service;

import com.example.autoHRM_backend.auth.service.EmployeeUserDetails;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.employee.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 서비스로 등록해야, authenManager이 이걸 사용함
@Service
public class EmployeeUserDetailService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeUserDetailService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null) {

            //return 하면 authenticateManger 이 검증함
            return new EmployeeUserDetails(employee);
        }

        return null;
    }
}
