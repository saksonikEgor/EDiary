package com.saksonik.headmanager.service;

import com.saksonik.headmanager.dto.CallScheduleDTO;
import com.saksonik.headmanager.model.ScheduledCall;
import com.saksonik.headmanager.repository.ScheduledCallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduledCallService {
    private final ScheduledCallRepository scheduledCallRepository;

    public List<ScheduledCall> findAll() {
        List<ScheduledCall> calls = scheduledCallRepository.findAll();
        calls.sort(Comparator.comparingInt(ScheduledCall::getLessonNumber));

        return calls;
    }

    @Transactional
    public void update(CallScheduleDTO callScheduleDTO) {
        Map<Integer, ScheduledCall> numberToCall = new HashMap<>();

        scheduledCallRepository.findAllByLessonNumberContaining(callScheduleDTO.getCalls()
                        .stream()
                        .map(CallScheduleDTO.CallDTO::getNumber)
                        .toList())
                .forEach(call -> numberToCall.put(call.getLessonNumber(), call));

        for (CallScheduleDTO.CallDTO callDTO : callScheduleDTO.getCalls()) {
            ScheduledCall call = numberToCall.computeIfAbsent(callDTO.getNumber(), k -> {
                ScheduledCall scheduledCall = new ScheduledCall();
                scheduledCall.setLessonNumber(k);
                return scheduledCall;
            });

            call.setEnd(callDTO.getEnd());
            call.setStart(callDTO.getStart());
        }

        scheduledCallRepository.saveAll(numberToCall.values());
    }
}
