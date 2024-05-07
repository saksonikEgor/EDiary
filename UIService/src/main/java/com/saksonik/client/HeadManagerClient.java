package com.saksonik.client;

import com.saksonik.dto.Classroom;
import com.saksonik.dto.callSchedule.ScheduledCallDTO;
import com.saksonik.dto.meetings.CreateMeetingRequest;
import com.saksonik.dto.meetings.MeetingScheduleDTO;
import com.saksonik.dto.meetings.UpdateMeetingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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


}
