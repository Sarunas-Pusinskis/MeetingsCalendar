package com.example.meetingscalendar.meetings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingService {
    private List<Meeting> meetings;
    private static final String MEETINGS_FILE = "meetings.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public MeetingService() {
        loadMeetingsFromFile();
    }

    private void loadMeetingsFromFile() {
        try {
            File file = new File(MEETINGS_FILE);
            if (file.exists()) {
                meetings = objectMapper.readValue(file, new TypeReference<>() {
                });
            } else {
                meetings = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            meetings = new ArrayList<>();
        }
    }

    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
        saveMeetingsToFile();
    }
    private void saveMeetingsToFile() {
        try {
            objectMapper.writeValue(new File(MEETINGS_FILE), meetings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Meeting> getAllMeetings() {
        return meetings;
    }

    public void deleteMeeting(String name, String responsiblePerson) {
        meetings.removeIf(meeting -> meeting.getName().equalsIgnoreCase(name) &&
                meeting.getResponsiblePerson().equalsIgnoreCase(responsiblePerson));
        saveMeetingsToFile();
    }






















}
