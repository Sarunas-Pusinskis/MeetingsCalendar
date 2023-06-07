package com.example.meetingscalendar.meetings;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meetings")
public class MeetingController {
private final MeetingService meetingService;
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }
    @GetMapping
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        List<Meeting> meetings = meetingService.getAllMeetings();
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }
    // localhost:8080/meetings/create-meeting
    /* JSON:
     {
      "name": "Juniors",
      "responsiblePerson": "Jo",
      "description": "Who reached Mid-level",
      "category": "CodeMonkey",
      "type": "Live",
      "startDate": "2023-06-06",
      "endDate": "2023-06-07"
    } */
    @PostMapping("/create-meeting")
    public ResponseEntity<String> createMeeting (@RequestBody Meeting meeting) {
        meetingService.createMeeting(meeting);
        return new ResponseEntity<>("Meeting created successfully.", HttpStatus.CREATED);
    }















}
