package com.example.autoHRM_backend.auth.service;

import com.example.autoHRM_backend.domain.employee.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class EmployeeUserDetails implements UserDetails {

    private final Employee employee;

    public EmployeeUserDetails(Employee employee) {
        this.employee = employee;
    }

    // 권한이 있는지를 검사
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return employee.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return employee.getPassword();
    }

    @Override
    public String getUsername() {

        return employee.getName();
    }

    public String getEmail(){
        return employee.getEmail();
    }

    public Long getId(){return employee.getId();}

    // 밑에는 계정 블락 여부 설정임
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}