package com.saksonik.dto.marks;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMarkRequest{
        UUID studentId;
        UUID subjectId;

        @NotNull(message = "Необходимо выбрать оценку")
        UUID markTypeId;

        @NotNull(message = "Необходимо выбрать тип работы")
        UUID workTypeId;

        @NotEmpty(message = "Описание должно быть не пусто")
        @Size(min = 5, max = 250, message = "Длина описания должна быть от 10 до 250")
        String description;

        @NotNull(message = "Дата должна быть не пуста")
        LocalDate date;
}

