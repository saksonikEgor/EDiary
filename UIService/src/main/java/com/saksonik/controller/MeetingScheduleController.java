package com.saksonik.controller;

import com.saksonik.client.HeadManagerClient;
import com.saksonik.dto.meetings.CreateMeetingRequest;
import com.saksonik.dto.meetings.UpdateMeetingRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/meeting-schedule")
@RequiredArgsConstructor
public class MeetingScheduleController {
    private final HeadManagerClient headManagerClient;

    @GetMapping("/list/{classId}")
    public Mono<String> getMeetingScheduleForClass(Model model, @PathVariable("classId") UUID classId) {
        return headManagerClient.getMeetingSchedule(classId)
                .doOnNext(meetingSchedule -> model.addAttribute("meetingSchedule", meetingSchedule))
                .thenReturn("meetings/meeting-schedule");
    }

    @GetMapping("/create/{classId}")
    public Mono<String> getCreateMeetingPage(@ModelAttribute("meeting") CreateMeetingRequest meeting,
                                             @PathVariable("classId") UUID classId,
                                             Model model) {
//        meeting.setClassId(classId);
        model.addAttribute("classId", classId);
        model.addAttribute("meeting", meeting);

        return headManagerClient.getAllClassrooms()
                .collectList()
                .doOnNext(classrooms -> model.addAttribute("classrooms", classrooms))
                .thenReturn("meetings/create");
    }

    @GetMapping("/update/{classId}/{id}")
    public Mono<String> getUpdateMeetingPage(@ModelAttribute("payload") UpdateMeetingRequest payload,
                                             @PathVariable("classId") UUID classId,
                                             @PathVariable("id") UUID meetingId,
                                             Model model) {
        model.addAttribute("classId", classId);

        headManagerClient.getMeetingById(meetingId)
                .doOnNext(meeting -> model.addAttribute("meeting", meeting))
                .block();

        return headManagerClient.getAllClassrooms()
                .collectList()
                .doOnNext(classrooms -> model.addAttribute("classrooms", classrooms))
                .thenReturn("meetings/update");
    }

    @PostMapping("/create/{classId}")
    public Mono<String> createMeeting(@PathVariable("classId") UUID classId,
                                      @ModelAttribute("meeting") @Valid CreateMeetingRequest meeting,
                                      BindingResult bindingResult,
                                      Model model) {
        log.info("Creating meeting {}", meeting);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            model.addAttribute("meeting", meeting);
            return Mono.just("meetings/create/%s".formatted(classId));
        } else {
            return headManagerClient.createMeeting(meeting)
                    .thenReturn("redirect:/meeting-schedule/list/%s".formatted(classId));
        }
    }

    @PostMapping("/update/{classId}/{id}")
    public Mono<String> updateMeeting(@PathVariable("classId") UUID classId,
                                      @PathVariable("id") UUID meetingId,
                                      @ModelAttribute("payload") @Valid UpdateMeetingRequest payload,
                                      BindingResult bindingResult,
                                      Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            model.addAttribute("payload", payload);

            headManagerClient.getMeetingById(meetingId)
                    .doOnNext(meeting -> model.addAttribute("meeting", meeting))
                    .block();

            return Mono.just("meetings/update/%s/%s".formatted(classId, meetingId));
        } else {
            return headManagerClient.updateMeeting(meetingId, payload)
                    .thenReturn("redirect:/meeting-schedule/list/%s".formatted(classId));
        }
    }

    @PostMapping("/delete/{classId}/{id}")
    public Mono<String> deleteMeeting(@PathVariable("classId") UUID classId,
                                      @PathVariable("id") UUID meetingId) {
        return headManagerClient.deleteMeeting(meetingId)
                .thenReturn("redirect:/meeting-schedule/list/%s".formatted(classId));
    }
}
