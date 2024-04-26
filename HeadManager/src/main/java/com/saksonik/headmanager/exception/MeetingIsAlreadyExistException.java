package com.saksonik.headmanager.exception;

public class MeetingIsAlreadyExistException extends RuntimeException {
    public MeetingIsAlreadyExistException(String message) {
        super(message);
    }
}
