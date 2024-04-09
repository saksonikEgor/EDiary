package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.callSchedule.CallScheduleDTO;
import com.saksonik.headmanager.model.ScheduledCall;
import com.saksonik.headmanager.service.ScheduledCallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/call-schedule")
@RequiredArgsConstructor
public class CallScheduleController {
    private final ScheduledCallService scheduledCallService;

    @GetMapping
    public ResponseEntity<CallScheduleDTO> getCallSchedule() {
        List<ScheduledCall> calls = scheduledCallService.findAll();

        return ResponseEntity.ok(
                new CallScheduleDTO(calls.stream()
                        .map(call -> new CallScheduleDTO.CallDTO(
                                call.getLessonNumber(),
                                call.getStart(),
                                call.getEnd()))
                        .toList()
                )
        );

    }
}
