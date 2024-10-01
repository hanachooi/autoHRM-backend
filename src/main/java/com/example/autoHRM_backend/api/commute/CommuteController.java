package com.example.autoHRM_backend.api.commute;

import com.example.autoHRM_backend.auth.service.EmployeeUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommuteController {

    private final CommuteService commuteService;

    @PostMapping("/commute")
    public ResponseEntity<Void> checkIn(@RequestParam String email) throws Exception{
        System.out.println("CommuteController.checkIn");
        commuteService.checkIn(email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/commute")
    public ResponseEntity<Void> checkOut(@RequestParam String email) throws Exception{
        System.out.println("CommuteController.checkOut");
        commuteService.checkOut(email);
        return ResponseEntity.ok().build();
    }

    // 본인의 익일 근무 상태 확인 ( 출 / 퇴 )
    @GetMapping("/commute/status/my")
    public ResponseEntity<Boolean> checkInStatus() throws Exception{
        System.out.println("CommuteController.checkInStatus");


        // 토큰의 정보로 자신의 이메일을 가져오게 됌.
        String employeeLoginId = getEmployeeLoginId();
        boolean status = commuteService.checkInStatus(employeeLoginId);
        System.out.println(employeeLoginId);

        return ResponseEntity.ok(status);
    }


    // 자신의 이메일을 가져오는 함수
    private String getEmployeeLoginId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeUserDetails employeeUserDetails = (EmployeeUserDetails)principal;

        return employeeUserDetails.getEmail();
    }

}
