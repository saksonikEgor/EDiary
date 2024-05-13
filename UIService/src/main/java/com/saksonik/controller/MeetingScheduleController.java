package com.saksonik.controller;

import com.saksonik.client.HeadManagerClient;
import com.saksonik.dto.meetings.CreateMeetingRequest;
import com.saksonik.dto.meetings.UpdateMeetingRequest;
import com.saksonik.dto.userfeed.UserfeedDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/meeting-schedule")
@RequiredArgsConstructor
public class MeetingScheduleController {
    private final HeadManagerClient headManagerClient;

    @GetMapping("/list/{classId}")
    public Mono<String> getMeetingScheduleForClass(@ModelAttribute("userfeed") Mono<UserfeedDTO> userfeed,
                                                   Model model,
                                                   @PathVariable("classId") UUID classId) {
        return headManagerClient.getMeetingSchedule(classId)
                .doOnNext(meetingSchedule -> model.addAttribute("meetingSchedule", meetingSchedule))
                .then(userfeed.doOnNext(uf -> model.addAttribute("userfeed", uf)))
                .thenReturn("meetings/meeting-schedule");
    }

    @GetMapping("/create/{classId}")
    public Mono<String> getCreateMeetingPage(@ModelAttribute("userfeed") Mono<UserfeedDTO> userfeed,
                                             @ModelAttribute("meeting") CreateMeetingRequest meeting,
                                             @PathVariable("classId") UUID classId,
                                             Model model) {
        model.addAttribute("classId", classId);
        model.addAttribute("meeting", meeting);

        return headManagerClient.getAllClassrooms()
                .collectList()
                .doOnNext(classrooms -> model.addAttribute("classrooms", classrooms))
                .then(userfeed.doOnNext(uf -> model.addAttribute("userfeed", uf)))
                .thenReturn("meetings/create");
    }

    @GetMapping("/update/{classId}/{id}")
    public Mono<String> getUpdateMeetingPage(@ModelAttribute("userfeed") Mono<UserfeedDTO> userfeed,
                                             @ModelAttribute("payload") UpdateMeetingRequest payload,
                                             @PathVariable("classId") UUID classId,
                                             @PathVariable("id") UUID meetingId,
                                             Model model) {
        model.addAttribute("classId", classId);

        return headManagerClient.getMeetingById(meetingId)
                .doOnNext(meeting -> model.addAttribute("meeting", meeting))
                .then(headManagerClient.getAllClassrooms()
                        .collectList()
                        .doOnNext(classrooms -> model.addAttribute("classrooms", classrooms)))
                .then(userfeed.doOnNext(uf -> model.addAttribute("userfeed", uf)))
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

            return headManagerClient.getAllClassrooms()
                    .collectList()
                    .doOnNext(classrooms -> model.addAttribute("classrooms", classrooms))
                    .thenReturn("meetings/create");
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
        log.info("Updating meeting {}", payload);

        if (bindingResult.hasErrors()) {
            model.addAttribute("classId", classId);

            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            model.addAttribute("payload", payload);

            return headManagerClient.getMeetingById(meetingId)
                    .doOnNext(meeting -> model.addAttribute("meeting", meeting))
                    .then(headManagerClient.getAllClassrooms()
                            .collectList()
                            .doOnNext(classrooms -> model.addAttribute("classrooms", classrooms)))
                    .thenReturn("meetings/update");

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

    @ModelAttribute
    public Mono<CsrfToken> loadCsrfToken(ServerWebExchange exchange) {
        return Objects.requireNonNull(exchange.<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName()))
                .doOnSuccess(token ->
                        exchange.getAttributes().put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
    }

    @ModelAttribute(name = "userfeed", binding = false)
    public Mono<UserfeedDTO> loadUserfeed() {
        return headManagerClient.getUserfeed();
    }
}
