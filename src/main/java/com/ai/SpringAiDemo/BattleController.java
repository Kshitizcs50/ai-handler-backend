package com.ai.SpringAiDemo;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ai.SpringAiDemo.Repository.HeroRepository;

@RestController
@RequestMapping("/api/battle")
public class BattleController {

    private final HeroRepository heroRepository;
    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();

    @Autowired
    public BattleController(HeroRepository heroRepository,
                            RoomService roomService,
                            SimpMessagingTemplate messagingTemplate) {
        this.heroRepository = heroRepository;
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }

    // ---- 1v1 Battle ----
    @PostMapping("/fight")
    @PreAuthorize("isAuthenticated()")
    public BattleResult fight(@RequestBody FightRequest request) {
        Hero hero1 = heroRepository.findById(request.getHero1Id())
                .orElseThrow(() -> new RuntimeException("Hero1 not found"));
        Hero hero2 = heroRepository.findById(request.getHero2Id())
                .orElseThrow(() -> new RuntimeException("Hero2 not found"));

        return BattleService.fight(hero1, hero2);
    }

    // ---- Team Battle (2v2 or 4v4 AI) ----
    @PostMapping("/teamFight")
    @PreAuthorize("isAuthenticated()")
    public TeamBattleResult teamFight(@RequestBody TeamFightRequest request) {
        List<Hero> team1 = heroRepository.findAllById(request.getTeam1());
        List<Hero> team2 = heroRepository.findAllById(request.getTeam2());

        return BattleService.teamFight(team1, team2);
    }

    // ---- Get All Heroes ----
    @GetMapping("/heroes")
    @PreAuthorize("isAuthenticated()")
    public List<Hero> getHeroes() {
        return heroRepository.findAll();
    }

    // ---- WebSocket: join room ----

}
