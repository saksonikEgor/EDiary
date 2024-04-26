package com.saksonik.notificator.exception;

public class MessageSendingException extends RuntimeException {
    public MessageSendingException(String message) {
        super(message);
    }
}
