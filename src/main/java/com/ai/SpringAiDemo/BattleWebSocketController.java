package com.ai.SpringAiDemo;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.ai.SpringAiDemo.Repository.HeroRepository;

@Controller
public class BattleWebSocketController {

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // --- Payload classes ---
    static class JoinRoomMessage { public String roomId; }
    static class SelectHeroesMessage { public String roomId; public List<Long> heroes; }

    // --- Join room ---
    @MessageMapping("/joinRoom")
    public void joinRoom(@Payload JoinRoomMessage msg, @Header("simpSessionId") String sessionId) {
        roomService.addPlayer(msg.roomId, sessionId);

        List<String> players = roomService.getPlayers(msg.roomId);

        // Broadcast room status to all players
        messagingTemplate.convertAndSend("/topic/room/" + msg.roomId, Map.of("opponentPresent", true));

        System.out.println("✅ Player joined room: " + msg.roomId + " session=" + sessionId);

    }

    // --- Select heroes ---
    @MessageMapping("/selectHeroes")
    public void selectHeroes(@Payload SelectHeroesMessage msg, @Header("simpSessionId") String sessionId) {
        List<Hero> selectedHeroes = heroRepository.findAllById(msg.heroes);
        roomService.storeHeroes(msg.roomId, sessionId, selectedHeroes);

        // ✅ If both players ready → normal fight
        if (roomService.bothPlayersReady(msg.roomId)) {
            List<String> players = roomService.getPlayers(msg.roomId);
            List<Hero> p1Heroes = roomService.getHeroes(msg.roomId, players.get(0));
            List<Hero> p2Heroes = roomService.getHeroes(msg.roomId, players.get(1));

            TeamBattleResult result = BattleService.teamFight(p1Heroes, p2Heroes);
            System.out.println("✅ Sending fight result to /topic/room/" + msg.roomId + " -> " + result);


            messagingTemplate.convertAndSend(
            	    "/topic/room/" + msg.roomId,
            	    Map.of("fightResult", result)
            	);

        } else {
            // 👇 Add this block for single-player testing
            messagingTemplate.convertAndSendToUser(
                sessionId,
                "/topic/room/" + msg.roomId,
                Map.of("waiting", true, "msg", "Waiting for opponent...")
            );
        }
    }


    // --- Handle disconnect ---
    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();

        roomService.removePlayer(sessionId).forEach(roomId -> {
            messagingTemplate.convertAndSend(
                    "/topic/room/" + roomId,
                    Map.of("opponentPresent", false)
            );
        });
    }
}
