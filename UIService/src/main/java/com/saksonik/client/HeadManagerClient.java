package com.saksonik.client;

import com.saksonik.dto.Classroom;
import com.saksonik.dto.callSchedule.ScheduledCallDTO;
import com.saksonik.dto.classes.ClassDTO;
import com.saksonik.dto.lessonTimetable.LessonTimetableDTO;
import com.saksonik.dto.meetings.CreateMeetingRequest;
import com.saksonik.dto.meetings.MeetingScheduleDTO;
import com.saksonik.dto.meetings.UpdateMeetingRequest;
import com.saksonik.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
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
                .bodyToMono(MeetingScheduleDTO.class);
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
                .bodyToMono(Void.class);
    }

    public Mono<MeetingScheduleDTO.MeetingDTO> getMeetingById(UUID meetingId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/meeting-schedule/{id}")
                        .build(meetingId))
                .retrieve()
                .bodyToMono(MeetingScheduleDTO.MeetingDTO.class);
    }

    public Mono<Void> updateMeeting(UUID meetingId, UpdateMeetingRequest request) {
        return webClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/meeting-schedule/{id}")
                        .build(meetingId))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteMeeting(UUID meetingId) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/meeting-schedule/{id}")
                        .build(meetingId))
                .retrieve()
                .bodyToMono(Void.class);
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
                .bodyToFlux(LessonTimetableDTO.class);
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
                .bodyToFlux(LessonTimetableDTO.class);
    }

    public Mono<UserDTO> getUserById(UUID userId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/{userId}")
                        .build(userId))
                .retrieve()
                .bodyToMono(UserDTO.class);
    }

    public Mono<ClassDTO> getClassById(UUID classId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/class/{classId}")
                        .build(classId))
                .retrieve()
                .bodyToMono(ClassDTO.class);
    }


}
