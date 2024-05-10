package com.saksonik.exception;

public class WrongRequestException extends RuntimeException {
    public WrongRequestException(String message) {
        super(message);
    }
}
