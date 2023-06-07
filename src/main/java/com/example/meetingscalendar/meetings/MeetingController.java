package com.example.meetingscalendar.meetings;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    // localhost:8080/meetings/delete-meeting/{name}/{responsiblePerson}
    @DeleteMapping("/delete-meeting/{name}/{responsiblePerson}")
    public ResponseEntity<String> deleteMeeting(@PathVariable String name, @PathVariable String responsiblePerson) {
        meetingService.deleteMeeting(name, responsiblePerson);
        return new ResponseEntity<>("Meeting deleted successfully.", HttpStatus.OK);
    }

    //localhost:8080/meetings/{meetingName}/add-person
    /* JSON example:
    {
        "person": "Tadas",
        "time": "2023-06-10"
    }*/
    @PostMapping("/{meetingName}/add-person")
    public ResponseEntity<String> addPersonToMeeting(@PathVariable String meetingName, @RequestBody PersonRequest personRequest) {
        LocalDate time = personRequest.getTime();
        String person = personRequest.getPerson();
        meetingService.addPersonToMeeting(meetingName, person, time);
        return new ResponseEntity<>("Person added to the meeting successfully.", HttpStatus.OK);
    }

    // localhost:8080/meetings/{meetingName}/remove-person
    /*     JSON example:
    {
  "person": "Tomas"
    } */
    @PostMapping("/{meetingName}/remove-person")
    public ResponseEntity<String> removePersonFromMeeting(@PathVariable String meetingName, @RequestBody PersonRequest personRequest) {
        String person = personRequest.getPerson();
        meetingService.removePersonFromMeeting(meetingName, person);
        return new ResponseEntity<>("Person removed from the meeting successfully.", HttpStatus.OK);
    }


    // localhost:8080/meetings/description/{description}
    @GetMapping("/description/{description}")
    public ResponseEntity<List<Meeting>> filterMeetingsByDescription(@PathVariable String description) {
        List<Meeting> meetings = meetingService.filterMeetingsByDescription(description);
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }














}
