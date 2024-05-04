package com.saksonik.headmanager.service;

import com.saksonik.headmanager.dto.callSchedule.ScheduledCallDTO;
import com.saksonik.headmanager.exception.alreadyExist.ScheduledCallIsAlreadyExistException;
import com.saksonik.headmanager.exception.notExist.ScheduledCallNotExistException;
import com.saksonik.headmanager.model.ScheduledCall;
import com.saksonik.headmanager.repository.ScheduledCallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduledCallService {
    private final ScheduledCallRepository scheduledCallRepository;

    public List<ScheduledCall> findAll() {
        List<ScheduledCall> calls = scheduledCallRepository.findAll();
        calls.sort(Comparator.comparing(ScheduledCall::getLessonNumber));

        return calls;
    }

    public ScheduledCall findById(UUID id) {
        return scheduledCallRepository.findById(id)
                .orElseThrow(() -> new ScheduledCallNotExistException("Scheduled Call Not Found"));
    }

    @Transactional
    public ScheduledCall create(ScheduledCallDTO scheduledCallDTO) {
        if (scheduledCallRepository.findById(scheduledCallDTO.callNumber()).isPresent()) {
            throw new ScheduledCallIsAlreadyExistException("Scheduled Call Already Exist");
        }

        ScheduledCall scheduledCall = new ScheduledCall();

        scheduledCall.setLessonNumber(scheduledCallDTO.callNumber());
        scheduledCall.setStart(scheduledCallDTO.start());
        scheduledCall.setEnd(scheduledCallDTO.end());

        return scheduledCallRepository.save(scheduledCall);
    }

    @Transactional
    public ScheduledCall update(ScheduledCallDTO scheduledCallDTO) {
        ScheduledCall scheduledCall = findById(scheduledCallDTO.callNumber());

        scheduledCall.setStart(scheduledCallDTO.start());
        scheduledCall.setEnd(scheduledCallDTO.end());

        return scheduledCallRepository.save(scheduledCall);
    }

    @Transactional
    public void delete(UUID id) {
        scheduledCallRepository.delete(findById(id));
    }

    public List<ScheduledCall> findAllByIds(List<UUID> ids) {
        return scheduledCallRepository.findAllById(ids);
    }
}
