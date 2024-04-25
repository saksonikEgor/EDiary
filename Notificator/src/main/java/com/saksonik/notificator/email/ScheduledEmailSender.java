package com.saksonik.notificator.email;

import com.saksonik.notificator.model.Notification;
import com.saksonik.notificator.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableScheduling
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class ScheduledEmailSender {
    private static final String MESSAGE_NAME = "Новое уведомление";
    private final EmailService emailService;
    private final NotificationService notificationService;

    @Scheduled(fixedDelayString = "#{@schedulerInterval.toMillis()}")
    public void sendEmail() {
        log.info("Checking for notifications");
        List<Notification> notifications = notificationService.getNotificationsWithADateBeforeCurrent();

        for (Notification notification : notifications) {
            log.info("Sending notification: {}", notification);
            emailService.sendMassage(notification.getEmail(), MESSAGE_NAME, notification.getDescription());
        }

        notificationService.removeAl(notifications);
    }
}
