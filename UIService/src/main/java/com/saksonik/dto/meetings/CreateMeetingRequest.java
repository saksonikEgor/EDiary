package com.saksonik.dto.meetings;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CreateMeetingRequest {
    @NotNull(message = "Дата должна быть не пуста")
    @Future(message = "Нельзя создать собрание с прошедшей датой")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime dateTime;

    @NotEmpty(message = "Описание должно быть не пусто")
    @Size(min = 10, max = 250, message = "Длина описания должна быть от 10 до 250")
    String description;

    @NotNull
    UUID classId;

    @NotNull
    UUID classroomId;
}
