package com.example.meetingscalendar.meetings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Meeting findMeetingByName(String meetingName) {
        return meetings.stream()
                .filter(meeting -> meeting.getName().equals(meetingName))
                .findFirst()
                .orElse(null);
    }

    public void addPersonToMeeting(String meetingName, String person, LocalDate time) {
        Meeting meeting = findMeetingByName(meetingName);
        if (meeting != null) {
            // Check if the person is already in a meeting that intersects with the new meeting
            boolean isIntersecting = meetings.stream()
                    .anyMatch(m -> m != meeting &&
                            m.getAttendees().contains(person) &&
                            m.getStartDate().isBefore(time) &&
                            m.getEndDate().isAfter(time));

            if (isIntersecting) {
                throw new IllegalArgumentException("Warning: The person is already in a meeting that intersects with the new meeting.");
            }

            // Check if the person is already added to the meeting
            if (meeting.getAttendees().contains(person)) {
                throw new IllegalArgumentException("Person is already added to the meeting.");
            }

            // Add the person to the meeting
            meeting.getAttendees().add(person);
        } else {
            throw new MeetingNotFoundException("Meeting not found: " + meetingName);
        }
    }

    public static class MeetingNotFoundException extends RuntimeException {
        public MeetingNotFoundException(String message) {
            super(message);
        }
    }

    public Meeting getMeetingByName(String name) {
        return meetings.stream()
                .filter(meeting -> meeting.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void removePersonFromMeeting(String meetingName, String person) {
        Meeting meeting = getMeetingByName(meetingName);
        if (meeting != null) {
            if (meeting.getResponsiblePerson().equalsIgnoreCase(person)) {
                System.out.println("The responsible person cannot be removed from the meeting.");
                return;
            }

            meeting.getAttendees().remove(person);
            saveMeetingsToFile();
        } else {
            System.out.println("Meeting not found.");
        }
    }

    public List<Meeting> filterMeetingsByDescription(String description) {
        return meetings.stream()
                .filter(meeting -> meeting.getDescription().toLowerCase().contains(description.toLowerCase()))
                .collect(Collectors.toList());
    }






}
