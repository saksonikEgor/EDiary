package com.saksonik.headmanager.exception.alreadyExist;

public class MeetingIsAlreadyExistException extends RuntimeException {
    public MeetingIsAlreadyExistException(String message) {
        super(message);
    }
}
