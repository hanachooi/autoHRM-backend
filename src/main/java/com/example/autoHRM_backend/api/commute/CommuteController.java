package com.example.autoHRM_backend.api.commute;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

}
