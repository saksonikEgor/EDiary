package com.saksonik.headmanager.exception.alreadyExist;

public class LessonScheduleIsAlreadyExistException extends RuntimeException {
    public LessonScheduleIsAlreadyExistException(String message) {
        super(message);
    }
}
