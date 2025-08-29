package com.ai.SpringAiDemo;

import com.ai.SpringAiDemo.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // REST API: fetch all past messages
    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // REST API: save + broadcast message
    @PostMapping
    public Message saveMessage(@RequestBody Message message) {
        Message saved = messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/messages", saved);
        return saved;
    }

    // WebSocket endpoint: send chat messages
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message handleChat(Message message) {
        return messageRepository.save(message);
    }
}
