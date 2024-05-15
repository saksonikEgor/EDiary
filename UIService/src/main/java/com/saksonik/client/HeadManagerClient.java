package com.saksonik.client;

import com.saksonik.dto.callSchedule.ScheduledCallDTO;
import com.saksonik.dto.classes.ClassDTO;
import com.saksonik.dto.classroom.Classroom;
import com.saksonik.dto.lessonTimetable.LessonTimetableDTO;
import com.saksonik.dto.markType.MarkTypeDTO;
import com.saksonik.dto.marks.CreateMarkRequest;
import com.saksonik.dto.marks.MarksDTO;
import com.saksonik.dto.meetings.CreateMeetingRequest;
import com.saksonik.dto.meetings.MeetingScheduleDTO;
import com.saksonik.dto.meetings.UpdateMeetingRequest;
import com.saksonik.dto.srudyPeriod.StudyPeriodDTO;
import com.saksonik.dto.subject.SubjectDTO;
import com.saksonik.dto.user.UserDTO;
import com.saksonik.dto.userfeed.UserfeedDTO;
import com.saksonik.dto.workType.WorkTypeDTO;
import com.saksonik.exception.HeadManagerAPIException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
public class HeadManagerClient {
    private final WebClient webClient;
    private final Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction = response ->
            Mono.error(new HeadManagerAPIException(switch (response.statusCode().value()) {
                case 400 -> "Некорректные параметры запроса";
                case 401, 403 -> "Недостаточно прав";
                case 404 -> "Страница не найдена";
                case 500 -> "Ошибка на стороне сервера";
                default -> "Неизвестная ошибка";
            }, response.statusCode().value()));

    public Mono<MeetingScheduleDTO> getMeetingSchedule(UUID classId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/meeting-schedule/list/{id}")
                        .build(classId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(MeetingScheduleDTO.class);
    }

    public Flux<Classroom> getAllClassrooms() {
        return webClient.get()
                .uri("/classroom")
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToFlux(Classroom.class);
    }

    public Mono<Void> createMeeting(CreateMeetingRequest request) {
        return webClient.post()
                .uri("/meeting-schedule")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(Void.class);
    }

    public Mono<MeetingScheduleDTO.MeetingDTO> getMeetingById(UUID meetingId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/meeting-schedule/{id}")
                        .build(meetingId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(MeetingScheduleDTO.MeetingDTO.class);
    }

    public Mono<Void> updateMeeting(UUID meetingId, UpdateMeetingRequest request) {
        return webClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/meeting-schedule/{id}")
                        .build(meetingId))
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteMeeting(UUID meetingId) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/meeting-schedule/{id}")
                        .build(meetingId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(Void.class);
    }

    public Flux<ScheduledCallDTO> getCallSchedule() {
        return webClient.get()
                .uri("/call-schedule")
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
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
                .onStatus(HttpStatusCode::isError, exceptionFunction)
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
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToFlux(LessonTimetableDTO.class);
    }

    public Mono<UserDTO> getUserById(UUID userId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/{userId}")
                        .build(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(UserDTO.class);
    }

    public Mono<ClassDTO> getClassById(UUID classId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/class/{classId}")
                        .build(classId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(ClassDTO.class);
    }

    public Mono<MarksDTO> getMarksForStudentAndPeriod(UUID studentId, UUID studyPeriodId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/marks/for-student/{studentId}")
                        .queryParam("period", studyPeriodId)
                        .build(studentId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(MarksDTO.class);
    }

    public Mono<MarksDTO> getMarksForClassAndSubjectAndStudyPeriod(UUID classId, UUID subjectId, UUID studyPeriodId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/marks/for-class/{classId}/{subjectId}")
                        .queryParam("period", studyPeriodId)
                        .build(classId, subjectId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(MarksDTO.class);
    }

    public Flux<SubjectDTO> findAllSubjectsByStudentId(UUID studentId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/subject/for-student/{studentId}")
                        .build(studentId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToFlux(SubjectDTO.class);
    }

    public Flux<UserDTO> findAllStudentsByClass(UUID classId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/students/{classId}")
                        .build(classId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToFlux(UserDTO.class);
    }

    public Mono<SubjectDTO> findSubjectById(UUID subjectId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/subject/{subjectId}")
                        .build(subjectId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(SubjectDTO.class);
    }

    public Mono<UserfeedDTO> getUserfeed() {
        return webClient.get()
                .uri("/userfeed")
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(UserfeedDTO.class);
    }

    public Flux<MarkTypeDTO> getAllMarkTypes() {
        return webClient.get()
                .uri("/mark-type/list")
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToFlux(MarkTypeDTO.class);
    }

    public Flux<WorkTypeDTO> getAllWorkTypes() {
        return webClient.get()
                .uri("/work-type/list")
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToFlux(WorkTypeDTO.class);
    }

    public Flux<StudyPeriodDTO> getAllStudyPeriods() {
        return webClient.get()
                .uri("/study-period/list")
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToFlux(StudyPeriodDTO.class);
    }

    public Mono<Void> createMark(CreateMarkRequest request) {
        return webClient.post()
                .uri("/marks")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, exceptionFunction)
                .bodyToMono(Void.class);
    }


}
