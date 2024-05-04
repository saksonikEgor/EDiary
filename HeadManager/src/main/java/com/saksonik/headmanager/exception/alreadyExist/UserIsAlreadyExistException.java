package com.saksonik.headmanager.exception.alreadyExist;

public class UserIsAlreadyExistException extends RuntimeException {
    public UserIsAlreadyExistException(String message) {
        super(message);
    }
}
