package com.saksonik.dto.meetings;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CreateMeetingRequest {
    OffsetDateTime dateTime;
    String description;
    UUID classId;
    UUID classroomId;
}
