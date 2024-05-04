package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.MeetingScheduleDTO;
import com.saksonik.headmanager.dto.meetings.CreateMeetingRequest;
import com.saksonik.headmanager.dto.meetings.MeetingResponse;
import com.saksonik.headmanager.dto.meetings.UpdateMeetingRequest;
import com.saksonik.headmanager.exception.NoAuthorityException;
import com.saksonik.headmanager.model.*;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.service.ClassService;
import com.saksonik.headmanager.service.ClassroomService;
import com.saksonik.headmanager.service.MeetingService;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @GetMapping("/{id}")
    public ResponseEntity<MeetingScheduleDTO> getMeetingsScheduleByClass(
            @RequestHeader("User-Id") UUID userId,
            @PathVariable("id") UUID classId,
            @RequestHeader("Role") String role
    ) {
//        String role = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getAuthorities()
//                .stream()
//                .findAny()
//                .get()
//                .getAuthority();

        MeetingScheduleDTO meetingScheduleDTO = new MeetingScheduleDTO();
        meetingScheduleDTO.setRole(role);

        User user = userService.findUserById(userId); //TODO ?????
        Class c = classService.findById(classId);

        switch (role) {
            case "ROLE_STUDENT", "ROLE_PARENT", "ROLE_CLASSROOM-TEACHER" ->
                    meetingScheduleDTO.setMeetings(c.getMeetings().stream()
                            .map(meeting -> new MeetingScheduleDTO.MeetingDTO(
                                    meeting.getClazz().getName(),
                                    meeting.getClassroom().getName(),
                                    meeting.getMeetingDateTime(),
                                    meeting.getDescription()))
                            .toList());
            case null, default -> throw new NoAuthorityException("Not allowed to access this meeting");
        }
        return ResponseEntity.ok(meetingScheduleDTO);
    }

    //TODO  проверить иммет ли юзер права
    @GetMapping
    public ResponseEntity<MeetingScheduleDTO> getMeetingsScheduleForClassroomTeacher(
            @RequestHeader("User-Id") UUID userId,
            @RequestHeader("Role") String role
    ) {
//        String role = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getAuthorities()
//                .stream()
//                .findAny()
//                .get()
//                .getAuthority();


        MeetingScheduleDTO meetingScheduleDTO = new MeetingScheduleDTO();
        meetingScheduleDTO.setRole(role);

        User user = userService.findUserById(userId);
        List<Meeting> meetings = meetingService.findAllByClassroomTeacher(user);

        meetingScheduleDTO.setMeetings(meetings.stream()
                .map(meeting -> new MeetingScheduleDTO.MeetingDTO(
                        meeting.getClazz().getName(),
                        meeting.getClassroom().getName(),
                        meeting.getMeetingDateTime(),
                        meeting.getDescription()))
                .toList());

        return ResponseEntity.ok(meetingScheduleDTO);
    }

    //TODO  проверить иммет ли юзер права
    @PostMapping
    public ResponseEntity<MeetingResponse> createMeeting(
            @RequestHeader("User-Id") UUID userId,
            @RequestBody CreateMeetingRequest request) {
        User user = userService.findUserById(userId);
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
    public ResponseEntity<MeetingResponse> updateMeeting(
            @RequestHeader("User-Id") UUID userId,
            @PathVariable("id") UUID meetingId,
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
    public ResponseEntity<Void> deleteMeeting(
            @RequestHeader("User-Id") UUID userId,
            @PathVariable("id") UUID meetingId) {
        meetingService.delete(meetingId);
        return ResponseEntity.ok().build();
    }
}
