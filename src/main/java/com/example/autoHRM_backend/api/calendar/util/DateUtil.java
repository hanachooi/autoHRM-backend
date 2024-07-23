package com.example.autoHRM_backend.api.calendar.util;

import com.google.api.services.calendar.model.EventDateTime;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {

    /**
     * EventDateTime 에서 LocalDateTime으로 변환하는 함수
     *
     * @param eventDateTime
     * @return
     */
    public LocalDate convertToLocalDate(EventDateTime eventDateTime) {
        if (eventDateTime.getDateTime() != null) {
            return LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(eventDateTime.getDateTime().getValue()),
                    ZoneId.systemDefault()
            ).toLocalDate();
        } else if (eventDateTime.getDate() != null) {
            return LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(eventDateTime.getDate().getValue()),
                    ZoneId.systemDefault()
            ).toLocalDate(); // No need to set time parts to zero since LocalDate doesn't consider time
        } else {
            throw new IllegalArgumentException("Invalid EventDateTime: Both dateTime and date are null.");
        }
    }
}
