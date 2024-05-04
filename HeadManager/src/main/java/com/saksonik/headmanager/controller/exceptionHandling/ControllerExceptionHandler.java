package com.saksonik.headmanager.controller.exceptionHandling;

import com.saksonik.headmanager.dto.APIErrorResponse;
import com.saksonik.headmanager.exception.NoAuthorityException;
import com.saksonik.headmanager.exception.WrongLessonTimetableCredentialsException;
import com.saksonik.headmanager.exception.alreadyExist.LessonScheduleIsAlreadyExistException;
import com.saksonik.headmanager.exception.alreadyExist.MeetingIsAlreadyExistException;
import com.saksonik.headmanager.exception.alreadyExist.ScheduledCallIsAlreadyExistException;
import com.saksonik.headmanager.exception.notExist.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(LessonScheduleIsAlreadyExistException.class)
    public ResponseEntity<APIErrorResponse> ds(LessonScheduleIsAlreadyExistException exception) {
        return handleException(exception, HttpStatus.NOT_ACCEPTABLE, "Расписание занятий уже существует");
    }

    @ExceptionHandler(MeetingIsAlreadyExistException.class)
    public ResponseEntity<APIErrorResponse> ds(MeetingIsAlreadyExistException exception) {
        return handleException(exception, HttpStatus.NOT_ACCEPTABLE, "Собрание уже существует");
    }

    @ExceptionHandler(ScheduledCallIsAlreadyExistException.class)
    public ResponseEntity<APIErrorResponse> ds(ScheduledCallIsAlreadyExistException exception) {
        return handleException(exception, HttpStatus.NOT_ACCEPTABLE, "Расписание звонка уже существует");
    }

    @ExceptionHandler(ClassNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(ClassNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Класс не существует");
    }

    @ExceptionHandler(ClassroomNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(ClassroomNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Аудитория не существует");
    }

    @ExceptionHandler(LessonSchedulesIsNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(LessonSchedulesIsNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Расписание занятий не существует");
    }

    @ExceptionHandler(MarkNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(MarkNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Оценка не существует");
    }

    @ExceptionHandler(MarkTypeNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(MarkTypeNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Тип оценки не существует");
    }

    @ExceptionHandler(MeetingNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(MeetingNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Собрание не существует");
    }

    @ExceptionHandler(ScheduledCallNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(ScheduledCallNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Расписание звонка не существует");
    }

    @ExceptionHandler(StudyPeriodNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(StudyPeriodNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Учебный период не существует");
    }

    @ExceptionHandler(SubjectNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(SubjectNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Учебный предмет не существует");
    }

    @ExceptionHandler(TeacherNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(TeacherNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Учитель не существует");
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(UserNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Пользователь не существует");
    }

    @ExceptionHandler(WorkTypeNotExistException.class)
    public ResponseEntity<APIErrorResponse> ds(WorkTypeNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Тип работы не существует");
    }

    @ExceptionHandler(NoAuthorityException.class)
    public ResponseEntity<APIErrorResponse> ds(NoAuthorityException exception) {
        return handleException(exception, HttpStatus.FORBIDDEN, "Отсутствуют необходимые права даступа");
    }

    @ExceptionHandler(WrongLessonTimetableCredentialsException.class)
    public ResponseEntity<APIErrorResponse> ds(WrongLessonTimetableCredentialsException exception) {
        return handleException(exception, HttpStatus.BAD_REQUEST, "Неверный формат запроса с расписанием занятий");
    }

    private @NotNull ResponseEntity<APIErrorResponse> handleException(
            @NotNull Exception exception,
            @NotNull HttpStatus status,
            String description
    ) {
        return ResponseEntity.status(status).body(new APIErrorResponse(
                description,
                String.valueOf(status.value()),
                exception.getClass().getSimpleName(),
                exception.getMessage()
        ));
    }
}
