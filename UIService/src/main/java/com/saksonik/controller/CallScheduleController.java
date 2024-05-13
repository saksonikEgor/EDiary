package com.saksonik.controller;

import com.saksonik.client.HeadManagerClient;
import com.saksonik.dto.callSchedule.ScheduledCallDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Comparator;

@Slf4j
@Controller
@RequestMapping("/call-schedule")
@RequiredArgsConstructor
public class CallScheduleController {
    private final HeadManagerClient headManagerClient;

    @GetMapping
    public Mono<String> getCallSchedule(Model model) {
        return headManagerClient.getCallSchedule()
                .collectMap(ScheduledCallDTO::callNumber, call -> call)
                .doOnNext(callSchedule -> model.addAttribute("callSchedule", callSchedule))
                .thenReturn("callSchedule/call-schedule");
    }
}
