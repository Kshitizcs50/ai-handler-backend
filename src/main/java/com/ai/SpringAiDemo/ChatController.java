package com.ai.SpringAiDemo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

@Controller
public class ChatController {

    // store heroes taken by anyone
    private final Set<String> takenHeroes = new HashSet<>();

    // 🔹 Chat messages & hero notifications
    @MessageMapping("/sendMessage")
    @SendTo("/topic/notification")
    public Message handleMessage(Message message) {
        if ("USER_TAKEN".equals(message.getType())) {
            if (message.getHero() != null) {
                takenHeroes.add(message.getHero());
            }
        } else if ("USER_RELEASED".equals(message.getType())) {
            if (message.getHero() != null) {
                takenHeroes.remove(message.getHero());
            }
        }
        return message; // forward to all clients
    }

    // 🔹 REST API: Disable hero (taken)
    @PostMapping("/api/heroes/{hero}/disable")
    public ResponseEntity<?> disableHero(@PathVariable String hero) {
        takenHeroes.add(hero);
        return ResponseEntity.ok().body(hero + " disabled");
    }

    // 🔹 REST API: Enable hero (release)
    @PostMapping("/api/heroes/{hero}/enable")
    public ResponseEntity<?> enableHero(@PathVariable String hero) {
        takenHeroes.remove(hero);
        return ResponseEntity.ok().body(hero + " enabled");
    }

    // 🔹 REST API: Get all currently taken heroes
    @GetMapping("/api/heroes/taken")
    public ResponseEntity<Set<String>> getTakenHeroes() {
        return ResponseEntity.ok(takenHeroes);
    }
}
