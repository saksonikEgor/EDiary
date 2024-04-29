package com.saksonik.headmanager.exception.alreadyExist;

public class ScheduledCallIsAlreadyExistException extends RuntimeException {
    public ScheduledCallIsAlreadyExistException(String message) {
        super(message);
    }
}
