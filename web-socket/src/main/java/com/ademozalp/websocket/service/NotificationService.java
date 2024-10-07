package com.ademozalp.websocket.service;

import com.ademozalp.websocket.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationService {
    private final List<Notification> notifications = new ArrayList<>();

    public Notification save(Notification notification) {
        notification.setContent(notification.getContent() + ": " + (notifications.size() + 1));
        notification.setDateTime(LocalDateTime.now().toString());
        notifications.add(notification);
        return notification;
    }

    public List<Notification> getAll() {
        return notifications;
    }

}
