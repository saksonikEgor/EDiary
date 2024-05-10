package com.saksonik.client;

import com.saksonik.dto.callSchedule.ScheduledCallDTO;
import com.saksonik.dto.classes.ClassDTO;
import com.saksonik.dto.classroom.Classroom;
import com.saksonik.dto.error.lessonTimetable.LessonTimetableDTO;
import com.saksonik.dto.marks.MarksDTO;
import com.saksonik.dto.meetings.CreateMeetingRequest;
import com.saksonik.dto.meetings.MeetingScheduleDTO;
import com.saksonik.dto.meetings.UpdateMeetingRequest;
import com.saksonik.dto.subject.SubjectDTO;
import com.saksonik.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class HeadManagerClient {
    private final WebClient webClient;

    public Mono<MeetingScheduleDTO> getMeetingSchedule(UUID classId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/meeting-schedule/list/{id}")
                        .build(classId))
                .retrieve()
                .bodyToMono(MeetingScheduleDTO.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Flux<Classroom> getAllClassrooms() {
        return webClient.get()
                .uri("/classroom")
                .retrieve()
                .bodyToFlux(Classroom.class);
    }

    public Mono<Void> createMeeting(CreateMeetingRequest request) {
        return webClient.post()
                .uri("/meeting-schedule")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorComplete(WebClientResponseException.BadRequest.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Mono<MeetingScheduleDTO.MeetingDTO> getMeetingById(UUID meetingId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/meeting-schedule/{id}")
                        .build(meetingId))
                .retrieve()
                .bodyToMono(MeetingScheduleDTO.MeetingDTO.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Mono<Void> updateMeeting(UUID meetingId, UpdateMeetingRequest request) {
        return webClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/meeting-schedule/{id}")
                        .build(meetingId))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorComplete(WebClientResponseException.BadRequest.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Mono<Void> deleteMeeting(UUID meetingId) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/meeting-schedule/{id}")
                        .build(meetingId))
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Flux<ScheduledCallDTO> getCallSchedule() {
        return webClient.get()
                .uri("/call-schedule")
                .retrieve()
                .bodyToFlux(ScheduledCallDTO.class);
    }

    public Flux<LessonTimetableDTO> getLessonTimetableForClass(UUID classId,
                                                               LocalDate firstDayOfWeek,
                                                               LocalDate lastDayOfWeek) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/lesson-timetable/for-class/{id}")
                        .queryParam("start", firstDayOfWeek)
                        .queryParam("end", lastDayOfWeek)
                        .build(classId))
                .retrieve()
                .bodyToFlux(LessonTimetableDTO.class)
                .onErrorComplete(WebClientResponseException.BadRequest.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Flux<LessonTimetableDTO> getLessonTimetableForTeacher(UUID teacherId,
                                                                 LocalDate firstDayOfWeek,
                                                                 LocalDate lastDayOfWeek) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/lesson-timetable/for-teacher/{id}")
                        .queryParam("start", firstDayOfWeek)
                        .queryParam("end", lastDayOfWeek)
                        .build(teacherId))
                .retrieve()
                .bodyToFlux(LessonTimetableDTO.class)
                .onErrorComplete(WebClientResponseException.BadRequest.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Mono<UserDTO> getUserById(UUID userId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/{userId}")
                        .build(userId))
                .retrieve()
                .bodyToMono(UserDTO.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Mono<ClassDTO> getClassById(UUID classId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/class/{classId}")
                        .build(classId))
                .retrieve()
                .bodyToMono(ClassDTO.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Mono<MarksDTO> getMarksForStudentAndPeriod(UUID studentId, UUID studyPeriodId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/marks/for-student/{studentId}")
                        .queryParam("period", studyPeriodId)
                        .build(studentId))
                .retrieve()
                .bodyToMono(MarksDTO.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Mono<MarksDTO> getMarksForClassAndSubjectAndStudyPeriod(UUID classId, UUID subjectId, UUID studyPeriodId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/marks/for-class/{classId}/{subjectId}")
                        .queryParam("period", studyPeriodId)
                        .build(classId, subjectId))
                .retrieve()
                .bodyToMono(MarksDTO.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Flux<SubjectDTO> findAllSubjectsByStudentId(UUID studentId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/subject/for-student/{studentId}")
                        .build(studentId))
                .retrieve()
                .bodyToFlux(SubjectDTO.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Flux<UserDTO> findAllStudentsByClass(UUID classId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/students/{classId}")
                        .build(classId))
                .retrieve()
                .bodyToFlux(UserDTO.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    public Mono<SubjectDTO> findSubjectById(UUID subjectId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/subject/{subjectId}")
                        .build(subjectId))
                .retrieve()
                .bodyToMono(SubjectDTO.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }


}
