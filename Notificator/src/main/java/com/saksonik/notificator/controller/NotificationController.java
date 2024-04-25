package com.saksonik.notificator.controller;

import com.saksonik.notificator.dto.APIErrorDTO;
import com.saksonik.notificator.dto.NotificationDTO;
import com.saksonik.notificator.email.EmailService;
import com.saksonik.notificator.exception.NotificationNotFoundException;
import com.saksonik.notificator.service.NotificationService;
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

    @GetMapping("/test")
    public ResponseEntity<Void> sendMassage(@RequestBody String text, @RequestParam String to) {
        emailService.sendMassage(to, "Some message", text);
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
        notificationService.addAll(notificationDTOList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteNotification(@RequestBody List<Integer> ids) {
        notificationService.removeAll(ids);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateNotification(@RequestBody List<NotificationDTO> notificationDTOList) {
        notificationService.updateAll(notificationDTOList);
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
                "",
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                e.getClass().getSimpleName(),
                e.getMessage()
        ));
    }
}
