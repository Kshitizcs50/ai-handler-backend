package com.ai.SpringAiDemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
public class WebSocketHandlerImpl extends TextWebSocketHandler {

    private final Map<String, Map<String, List<Map<String, Object>>>> rooms = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> payload = mapper.readValue(message.getPayload(), Map.class);
        String type = (String) payload.get("type");

        if ("selectHeroes".equals(type)) {
            String roomId = (String) payload.get("roomId");
            List<Map<String, Object>> heroes = (List<Map<String, Object>>) payload.get("heroes");

            rooms.putIfAbsent(roomId, new HashMap<>());
            rooms.get(roomId).put(session.getId(), heroes);

            // Send opponent's heroes if both players are ready
            if (rooms.get(roomId).size() == 2) {
                List<String> sessions = new ArrayList<>(rooms.get(roomId).keySet());
                List<Map<String, Object>> player1Heroes = rooms.get(roomId).get(sessions.get(0));
                List<Map<String, Object>> player2Heroes = rooms.get(roomId).get(sessions.get(1));

                Map<String, Object> msg1 = Map.of("type", "opponentSelected", "opponentHeroes", player2Heroes);
                Map<String, Object> msg2 = Map.of("type", "opponentSelected", "opponentHeroes", player1Heroes);

                sessions.forEach(id -> {
                    try {
                        WebSocketSession s = session.getAttributes().get(id) != null ? (WebSocketSession) session.getAttributes().get(id) : session;
                        if (id.equals(s.getId())) s.sendMessage(new TextMessage(mapper.writeValueAsString(msg1)));
                        else s.sendMessage(new TextMessage(mapper.writeValueAsString(msg2)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // Remove from all rooms
        rooms.values().forEach(room -> room.remove(session.getId()));
        rooms.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }
}
