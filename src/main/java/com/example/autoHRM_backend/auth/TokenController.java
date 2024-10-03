package com.example.autoHRM_backend.auth;

import com.example.autoHRM_backend.auth.service.EmployeeUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class TokenController {

    // 로그인된 회원의 정보를 추출
    private String getManagerLoginId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails) principal;

        return employeeUserDetails.getEmail();
    }
    @GetMapping("/token")
    public String getToken(){
        return getManagerLoginId();
    }
}
