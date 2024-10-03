package com.example.autoHRM_backend.auth.controller;

import com.example.autoHRM_backend.auth.dto.JoinDTO;
import com.example.autoHRM_backend.auth.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {

        System.out.println(joinDTO.getName());
        joinService.joinProcess(joinDTO);

        return "ok";
    }

}
