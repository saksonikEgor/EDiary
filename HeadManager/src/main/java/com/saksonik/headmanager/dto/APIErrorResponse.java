package com.saksonik.headmanager.dto;

public record APIErrorResponse(
        String description,
        String code,
        String exceptionName,
        String exceptionMassage) {
}
