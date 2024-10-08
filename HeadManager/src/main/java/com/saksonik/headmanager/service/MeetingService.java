package com.saksonik.headmanager.service;

import com.saksonik.headmanager.dto.meetings.CreateMeetingRequest;
import com.saksonik.headmanager.dto.meetings.UpdateMeetingRequest;
import com.saksonik.headmanager.exception.notExist.MeetingNotExistException;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.model.Classroom;
import com.saksonik.headmanager.model.Meeting;
import com.saksonik.headmanager.model.User;
import com.saksonik.headmanager.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public Meeting findById(UUID id) {
        return meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotExistException("Meeting not found"));
    }

    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    public List<Meeting> findAllByClassroomTeacher(User classroomTeacher) {
        return meetingRepository.findAllByClassroomTeacher(classroomTeacher);
    }

    @Transactional
    public Meeting create(CreateMeetingRequest request, Class c, Classroom classroom) {
        Meeting meeting = new Meeting();

        meeting.setClazz(c);
        meeting.setClassroom(classroom);
        meeting.setMeetingDateTime(request.dateTime());
        meeting.setDescription(request.description());

        return meetingRepository.save(meeting);
    }

    @Transactional
    public Meeting update(UUID meetingId, UpdateMeetingRequest request, Classroom classroom) {
        Meeting meeting = findById(meetingId);

        meeting.setClassroom(classroom);
        meeting.setMeetingDateTime(request.dateTime());
        meeting.setDescription(request.description());

        return meetingRepository.save(meeting);
    }

    @Transactional
    public void delete(UUID meetingId) {
        Meeting meeting = findById(meetingId);
        meetingRepository.delete(meeting);
    }
}
