package com.example.autoHRM_backend.api.calendar;

import com.example.autoHRM_backend.domain.calendar.Holiday;
import com.example.autoHRM_backend.domain.calendar.HolidayRepository;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarRequestInitializer;
import com.google.api.services.calendar.model.Events;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;


@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
@Service
public class CalendarService {

    @Value("${google.api.key}")
    private String GOOGLE_CALENDAR_API_KEY;

    private final static String GOOGLE_KOREA_HOLIDAY_CALENDAR_ID = "l9ijikc83v1ne5s61er6tava4iplm8id@import.calendar.google.com";
    private final static JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private final HolidayRepository holidayRepository;
    private final DateUtil dateUtil;

    /**
     * Google calendar API를 사용하기 위한 Calendar 서비스를 생성해서 반환하는 함수
     *
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private Calendar createGoogleCalendarService() throws GeneralSecurityException, IOException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return new Calendar.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName("autoHRM-calendar")
                .setCalendarRequestInitializer(new CalendarRequestInitializer(GOOGLE_CALENDAR_API_KEY))
                .build();
    }

    /**
     * 공휴일 데이터를 가져오는 API
     *
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public Events findHolidayFromGoogleCalendar() throws IOException, GeneralSecurityException {
        Calendar service = createGoogleCalendarService();

        // 구글 캘린더에서 이벤트들을 생성해서 내보내줌. 즉, holiday를 내보냄
        return service.events().list(GOOGLE_KOREA_HOLIDAY_CALENDAR_ID).execute();
    }

    // db 저장
    @Transactional
    public void saveHolidays() throws IOException, GeneralSecurityException {
        Events events = findHolidayFromGoogleCalendar();
        List<Holiday> holidays = events.getItems().stream().map(event -> new Holiday(
                event.getSummary(),
                dateUtil.convertEventDateTimeToLocalDateTime(event.getStart()),
                dateUtil.convertEventDateTimeToLocalDateTime(event.getEnd())
        )).collect(Collectors.toList());

        holidayRepository.saveAll(holidays);
    }

}
