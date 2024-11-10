package com.example.autoHRM_backend.api.complaint;

import com.example.autoHRM_backend.auth.service.AuthUtil;
import com.example.autoHRM_backend.domain.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

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

        return ok().build();
    }

    @GetMapping("/complaint/my")
    public ResponseEntity<List<ComplaintResponseDTO>> findMyComplaints() {
        Company company = authUtil.getMyCompany();
        List<ComplaintResponseDTO> dto = complaintService.findMyComplaints(company);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/complaint/count/my")
    public ResponseEntity<Long> findMyComplaintCount() {
        Company company = authUtil.getMyCompany();
        return ResponseEntity.ok(complaintService.findMyComplaintCount(company));
    }

}
