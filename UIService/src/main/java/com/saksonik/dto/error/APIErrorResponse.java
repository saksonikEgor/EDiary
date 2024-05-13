package com.saksonik.dto.error;

public record APIErrorResponse(
        String description,
        String code,
        String exceptionName,
        String exceptionMassage) {
}
