package com.saksonik.notificator.repository;

import com.saksonik.notificator.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByUserId(UUID userId);

    List<Notification> findAllByNoticeDateIsBefore(OffsetDateTime time);
}