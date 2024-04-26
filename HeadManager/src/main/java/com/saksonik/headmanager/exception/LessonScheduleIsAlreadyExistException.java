package com.saksonik.headmanager.exception;

public class LessonScheduleIsAlreadyExistException extends RuntimeException {
    public LessonScheduleIsAlreadyExistException(String message) {
        super(message);
    }
}
