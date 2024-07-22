package com.example.autoHRM_backend.api.calendar;

import com.google.api.services.calendar.model.EventDateTime;
import java.time.Instant;
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
    public static LocalDateTime convertEventDateTimeToLocalDateTime(EventDateTime eventDateTime) {
        if (eventDateTime.getDateTime() != null) {
            return LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(eventDateTime.getDateTime().getValue()),
                    ZoneId.systemDefault()
            );
        } else if (eventDateTime.getDate() != null) {
            return LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(eventDateTime.getDate().getValue()),
                    ZoneId.systemDefault()
            ).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else {
            throw new IllegalArgumentException("Invalid EventDateTime: Both dateTime and date are null.");
        }
    }
}
