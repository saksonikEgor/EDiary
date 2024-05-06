package com.saksonik.dto.meetings;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMeetingRequest {
    OffsetDateTime dateTime;
    String description;
    UUID classroomId;
}

