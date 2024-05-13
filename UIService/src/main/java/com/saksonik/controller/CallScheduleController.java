package com.saksonik.controller;

import com.saksonik.client.HeadManagerClient;
import com.saksonik.dto.callSchedule.ScheduledCallDTO;
import com.saksonik.dto.userfeed.UserfeedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequestMapping("/call-schedule")
@RequiredArgsConstructor
public class CallScheduleController {
    private final HeadManagerClient headManagerClient;

    @GetMapping
    public Mono<String> getCallSchedule(@ModelAttribute("userfeed") Mono<UserfeedDTO> userfeed, Model model) {
        return headManagerClient.getCallSchedule()
                .collectMap(ScheduledCallDTO::callNumber, call -> call)
                .doOnNext(callSchedule -> model.addAttribute("callSchedule", callSchedule))
                .then(userfeed.doOnNext(uf -> model.addAttribute("userfeed", uf)))
                .thenReturn("callSchedule/call-schedule");
    }

    @ModelAttribute(name = "userfeed", binding = false)
    public Mono<UserfeedDTO> loadUserfeed() {
        return headManagerClient.getUserfeed();
    }
}
