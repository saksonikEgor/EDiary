package com.saksonik.notificator.controller;

import com.saksonik.notificator.dto.APIErrorDTO;
import com.saksonik.notificator.dto.MessageDTO;
import com.saksonik.notificator.dto.NotificationDTO;
import com.saksonik.notificator.email.EmailService;
import com.saksonik.notificator.exception.MessageSendingException;
import com.saksonik.notificator.exception.NotificationNotFoundException;
import com.saksonik.notificator.service.NotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class NotificationController {
    private final NotificationService notificationService;
    private final EmailService emailService;

    @PostMapping("/message")
    public ResponseEntity<Void> sendMassage(@RequestBody MessageDTO message) {
        try {
            emailService.sendMassage(message.receiver(), message.messageName(), message.text());
        } catch (MessagingException e) {
            log.error("Failed to send massage", e);
            throw new MessageSendingException("Cant send message " + message);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.findAll()
                .stream()
                .map(n -> new NotificationDTO(
                        n.getNotificationId(),
                        n.getUserId(),
                        n.getNoticeDate(),
                        n.getEmail(),
                        n.getDescription()
                ))
                .toList());
    }

    @PostMapping
    public ResponseEntity<Void> createNotification(@RequestBody List<NotificationDTO> notificationDTOList) {
        notificationService.add(notificationDTOList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteNotification(@RequestBody List<Integer> ids) {
        notificationService.remove(ids);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateNotification(@RequestBody List<NotificationDTO> notificationDTOList) {
        notificationService.update(notificationDTOList);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateEmail(@PathVariable UUID userId,
                                            @RequestBody String email) {
        notificationService.updateEmail(userId, email);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<APIErrorDTO> notificationNotFound(NotificationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIErrorDTO(
                "Notification not exist",
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                e.getClass().getSimpleName(),
                e.getMessage()
        ));
    }

    @ExceptionHandler(MessageSendingException.class)
    public ResponseEntity<APIErrorDTO> sendingMessageError(MessageSendingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIErrorDTO(
                "Cant send message",
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                e.getClass().getSimpleName(),
                e.getMessage()
        ));
    }
}
