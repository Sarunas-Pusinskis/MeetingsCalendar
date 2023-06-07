package com.example.meetingscalendar.meetings;

import java.time.LocalDate;

public class PersonRequest {
    private String person;
    private LocalDate time;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }
}
