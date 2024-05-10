package com.saksonik.exception;


import lombok.Getter;

public class HeadManagerAPIException extends RuntimeException {
    @Getter
    private final int statusCode;

    public HeadManagerAPIException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
