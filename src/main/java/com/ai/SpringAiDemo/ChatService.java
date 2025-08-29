package com.ai.SpringAiDemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.base-url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model; 

    public String chat(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        // Prepare request body
        var requestBody = Map.of(
            "model", model,
            "messages", List.of(
                Map.of("role", "user", "content", prompt)
            )
        );

        // Set headers
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Send request
        var entity = new HttpEntity<>(requestBody, headers);
        var response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

        // Parse response
        var body = response.getBody();
        if (body == null) return "No response from Groq";

        var choices = (List<Map<String, Object>>) body.get("choices");
        if (choices == null || choices.isEmpty()) return "No response from Groq";

        var message = (Map<String, Object>) choices.get(0).get("message");
        return message != null ? (String) message.get("content") : "No content in response";
    }
}
