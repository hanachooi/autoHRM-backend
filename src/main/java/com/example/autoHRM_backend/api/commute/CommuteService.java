package com.example.autoHRM_backend.api.commute;

import org.springframework.stereotype.Service;

@Service
public interface CommuteService {

    void checkIn(String email);

    void checkOut(String email);

}
