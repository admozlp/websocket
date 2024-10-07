package com.ademozalp.websocket.service;

import com.ademozalp.websocket.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationService {
    private final List<Message> messages = new ArrayList<>();

    public Message save(Message message) {
        message.setContent(message.getContent() + ": " + (messages.size() + 1));
        message.setDateTime(LocalDateTime.now().toString());
        messages.add(message);
        return message;
    }

    public List<Message> getAll() {
        return messages;
    }

}
