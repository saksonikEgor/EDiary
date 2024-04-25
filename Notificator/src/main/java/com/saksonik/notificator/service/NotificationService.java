package com.saksonik.notificator.service;

import com.saksonik.notificator.dto.NotificationDTO;
import com.saksonik.notificator.exception.NotificationNotFoundException;
import com.saksonik.notificator.model.Notification;
import com.saksonik.notificator.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Transactional
    public void add(List<NotificationDTO> notificationDTOList) {
        notificationRepository.saveAll(notificationDTOList.stream()
                .map(this::createNotification)
                .toList());
    }

    @Transactional
    public void remove(List<Integer> ids) {
        notificationRepository.deleteAllById(ids);
    }

    @Transactional
    public void update(List<NotificationDTO> notificationDTOList) {
        Map<Integer, NotificationDTO> notificationDTOMap = new HashMap<>();
        notificationDTOList.forEach(notificationDTO -> notificationDTOMap.put(notificationDTO.id(), notificationDTO));

        Set<Integer> ids = notificationDTOMap.keySet();

        List<Notification> notifications = notificationRepository.findAllById(ids);

        Set<Integer> totalIds = new HashSet<>(notifications.stream()
                .map(Notification::getNotificationId)
                .toList());

        for (Integer id : ids) {
            if (!totalIds.contains(id)) {
                throw new NotificationNotFoundException("Notification with id "
                        + id + " not found");
            }
        }

        for (Notification notification : notifications) {
            NotificationDTO dto = notificationDTOMap.get(notification.getNotificationId());

            notification.setUserId(dto.userId());
            notification.setEmail(dto.email());
            notification.setNoticeDate(dto.date());
            notification.setDescription(dto.description());
        }

        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void updateEmail(UUID userId, String email) {
        List<Notification> notifications = notificationRepository.findAllByUserId(userId);
        notifications.forEach(notification -> notification.setEmail(email));
        notificationRepository.saveAll(notifications);
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    private Notification createNotification(NotificationDTO notificationDTO) {
        Notification notification = new Notification();

        notification.setNotificationId(notificationDTO.id());
        notification.setUserId(notificationDTO.userId());
        notification.setEmail(notificationDTO.email());
        notification.setNoticeDate(notificationDTO.date());
        notification.setDescription(notificationDTO.description());

        return notification;
    }
}
