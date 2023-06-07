package com.example.meetingscalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MeetingsCalendarApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingsCalendarApplication.class, args);
    }

}
