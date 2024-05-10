package com.saksonik.dto;

public record APIErrorResponse(
        String description,
        String code,
        String exceptionName,
        String exceptionMassage) {
}
