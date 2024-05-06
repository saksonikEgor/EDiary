package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.MeetingScheduleDTO;
import com.saksonik.headmanager.dto.meetings.CreateMeetingRequest;
import com.saksonik.headmanager.dto.meetings.MeetingResponse;
import com.saksonik.headmanager.dto.meetings.UpdateMeetingRequest;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.Classroom;
import com.saksonik.headmanager.model.Meeting;
import com.saksonik.headmanager.service.ClassService;
import com.saksonik.headmanager.service.ClassroomService;
import com.saksonik.headmanager.service.MeetingService;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/meeting-schedule")
@RequiredArgsConstructor
public class MeetingScheduleController {
    private final MeetingService meetingService;
    private final UserService userService;
    private final ClassService classService;
    private final ClassroomService classroomService;

    //TODO  проверить иммет ли юзер права
    //TODO  добавить post запрос на добавления новых собраний
    //TODO  подумать мб разрешить этот метод только для админа
    @GetMapping("/list/{id}")
    public ResponseEntity<MeetingScheduleDTO> getMeetingsScheduleByClass(@PathVariable("id") UUID classId) {
        MeetingScheduleDTO meetingScheduleDTO = new MeetingScheduleDTO();

        Class c = classService.findById(classId);

        meetingScheduleDTO.setClassId(classId);
        meetingScheduleDTO.setClassName(c.getName());

        meetingScheduleDTO.setMeetings(c.getMeetings().stream()
                .map(meeting -> new MeetingScheduleDTO.MeetingDTO(
                        meeting.getMeetingId(),
                        meeting.getClassroom().getClassroomId(),
                        meeting.getClassroom().getName(),
                        meeting.getMeetingDateTime(),
                        meeting.getDescription()))
                .toList());

        return ResponseEntity.ok(meetingScheduleDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingScheduleDTO.MeetingDTO> getMeeetingById(@PathVariable("id") UUID meetingId) {
        Meeting meeting = meetingService.findById(meetingId);

        return ResponseEntity.ok(new MeetingScheduleDTO.MeetingDTO(
                meeting.getMeetingId(),
                meeting.getClassroom().getClassroomId(),
                meeting.getClassroom().getName(),
                meeting.getMeetingDateTime(),
                meeting.getDescription()));
    }

    //TODO  проверить иммет ли юзер права
    @PostMapping
    public ResponseEntity<MeetingResponse> createMeeting(@RequestBody CreateMeetingRequest request) {
        log.info("Creating meeting {}", request);

        Class c = classService.findById(request.classId());
        Classroom classroom = classroomService.findById(request.classroomId());

        Meeting meeting = meetingService.create(request, c, classroom);

        return ResponseEntity.ok(new MeetingResponse(
                meeting.getMeetingId(),
                meeting.getMeetingDateTime(),
                meeting.getDescription(),
                meeting.getClazz().getName(),
                meeting.getClassroom().getName()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MeetingResponse> updateMeeting(@PathVariable("id") UUID meetingId,
                                                         @RequestBody UpdateMeetingRequest request) {
        Classroom classroom = classroomService.findById(request.classroomId());

        Meeting meeting = meetingService.update(meetingId, request, classroom);

        return ResponseEntity.ok(new MeetingResponse(
                meeting.getMeetingId(),
                meeting.getMeetingDateTime(),
                meeting.getDescription(),
                meeting.getClazz().getName(),
                meeting.getClassroom().getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable("id") UUID meetingId) {
        meetingService.delete(meetingId);
        return ResponseEntity.ok().build();
    }
}
