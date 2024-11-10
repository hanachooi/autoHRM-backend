package com.example.autoHRM_backend.api.complaint;

import com.example.autoHRM_backend.auth.service.AuthUtil;
import com.example.autoHRM_backend.domain.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;
    private final AuthUtil authUtil;

    @PostMapping("/complaint")
    public ResponseEntity<Void> createComplaint(@RequestBody ComplaintRequestDTO complaintRequestDTO) {

        Company company = authUtil.getMyCompany();
        complaintService.createComplaint(company, complaintRequestDTO);

        return ResponseEntity.ok().build();
    }

}
