package com.ai.SpringAiDemo;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/groq")
public class GenAiController {

    private final ChatService chatService;

    public GenAiController(ChatService chatService) {
        this.chatService = chatService;
    }
    
   
    @PostMapping("/chat")
    public String getChatResponse(@RequestBody ChatRequest request) {
        return chatService.chat(request.getPrompt());
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam String prompt) {
        try {
            String reply = chatService.chat(prompt);
            return ResponseEntity.ok(reply);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error calling Groq: " + ex.getMessage());
        }
    }
}
