package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.callSchedule.ScheduledCallDTO;
import com.saksonik.headmanager.model.ScheduledCall;
import com.saksonik.headmanager.service.ScheduledCallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/call-schedule")
@RequiredArgsConstructor
public class CallScheduleController {
    private final ScheduledCallService scheduledCallService;

    @GetMapping
    public ResponseEntity<List<ScheduledCallDTO>> getCallSchedule() {
        List<ScheduledCall> calls = scheduledCallService.findAll();

        return ResponseEntity.ok(calls.stream()
                .map(c -> new ScheduledCallDTO(
                        c.getLessonNumber(),
                        c.getStart(),
                        c.getEnd()))
                .sorted(Comparator.comparing(ScheduledCallDTO::callNumber))
                .toList());
    }

    @PostMapping
    public ResponseEntity<ScheduledCallDTO> createCallSchedule(@RequestBody ScheduledCallDTO callScheduleDTO) {

        ScheduledCall scheduledCall = scheduledCallService.create(callScheduleDTO);

        return ResponseEntity.ok(
                new ScheduledCallDTO(
                        scheduledCall.getLessonNumber(),
                        scheduledCall.getStart(),
                        scheduledCall.getEnd()));
    }

    @PatchMapping
    public ResponseEntity<ScheduledCallDTO> update(@RequestBody ScheduledCallDTO callScheduleDTO) {
        ScheduledCall scheduledCall = scheduledCallService.update(callScheduleDTO);

        return ResponseEntity.ok(
                new ScheduledCallDTO(
                        scheduledCall.getLessonNumber(),
                        scheduledCall.getStart(),
                        scheduledCall.getEnd()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCallSchedule(@PathVariable("id") Integer callNumber) {
        scheduledCallService.delete(callNumber);
        return ResponseEntity.noContent().build();
    }
}
