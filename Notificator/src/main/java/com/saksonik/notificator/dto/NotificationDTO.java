package com.saksonik.notificator.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record NotificationDTO(int id, UUID userId, OffsetDateTime date, String email, String description) {
}
