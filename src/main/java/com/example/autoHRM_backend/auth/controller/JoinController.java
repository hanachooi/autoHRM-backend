package com.example.autoHRM_backend.auth.controller;

import com.example.autoHRM_backend.auth.dto.CompanyDTO;
import com.example.autoHRM_backend.auth.dto.JoinDTO;
import com.example.autoHRM_backend.auth.service.JoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDTO joinDTO) {

        System.out.println(joinDTO.getName());
        joinService.joinProcess(joinDTO);

        return "ok";
    }

    @PostMapping("/company")
    public ResponseEntity<Void> createCompany(@RequestBody CompanyDTO companyDTO) {

        System.out.println("JoinController.createCompany");
        joinService.createCompany(companyDTO);
        return ResponseEntity.ok().build();

    }

}
