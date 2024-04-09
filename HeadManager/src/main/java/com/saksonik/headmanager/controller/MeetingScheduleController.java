package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.MeetingScheduleDTO;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.Meeting;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.service.ClassService;
import com.saksonik.headmanager.service.MeetingService;
import com.saksonik.headmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    //TODO  проверить иммет ли юзер права
    //TODO  добавить post запрос на добавления новых собраний
    @GetMapping("/{id}")
    public ResponseEntity<MeetingScheduleDTO> getMeetingsScheduleByClass(
            @RequestHeader("User-Id") UUID userId,
            @PathVariable("id") Integer classId
    ) {
        String role = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findAny()
                .get()
                .getAuthority();

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
                                    meeting.getMeetingDate(),
                                    meeting.getDescription()))
                            .toList());
        }
        return ResponseEntity.ok(meetingScheduleDTO);
    }

    //TODO  проверить иммет ли юзер права
    @GetMapping
    public ResponseEntity<MeetingScheduleDTO> getMeetingsScheduleForClassroomTeacher(
            @RequestHeader("User-Id") UUID userId
    ) {
        String role = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findAny()
                .get()
                .getAuthority();

        MeetingScheduleDTO meetingScheduleDTO = new MeetingScheduleDTO();
        meetingScheduleDTO.setRole(role);

        User user = userService.findUserById(userId);
        List<Meeting> meetings = meetingService.findAllByClassroomTeacher(user);

        meetingScheduleDTO.setMeetings(meetings.stream()
                .map(meeting -> new MeetingScheduleDTO.MeetingDTO(
                        meeting.getClazz().getName(),
                        meeting.getClassroom().getName(),
                        meeting.getMeetingDate(),
                        meeting.getDescription()))
                .toList());

        return ResponseEntity.ok(meetingScheduleDTO);
    }
}
