package com.ai.SpringAiDemo;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import com.ai.SpringAiDemo.Repository.HeroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/heroes")
public class HeroController {

	private final Set<String> takenHeroes = ConcurrentHashMap.newKeySet();
	 @Autowired
	    private HeroRepository heroRepository;

	    // Existing APIs ...

	    // New API: disable hero
	 private Set<String> disabledHeroes = new HashSet<>();

	    @PostMapping("/{heroName}/disable")
	    public ResponseEntity<String> disableHero(@PathVariable String hero) {
	        disabledHeroes.add(hero);
	        return ResponseEntity.ok(hero + " disabled successfully");
	    }
	 @PutMapping("/disable/{id}")
	 public ResponseEntity<String> disableHero(@PathVariable Long id) {
	     return heroRepository.findById(id)
	         .map(hero -> {
	             hero.setDisabled(true);
	             heroRepository.save(hero);
	             return ResponseEntity.ok("Hero disabled successfully");
	         })
	         .orElse(ResponseEntity.notFound().build());
	 }


	    // New API: get all disabled heroes
	 @GetMapping("/disabled")
	    public ResponseEntity<Set<String>> getDisabledHeroes() {
	        return ResponseEntity.ok(disabledHeroes);
	    }

    @MessageMapping("/selectHero")
    @SendTo("/topic/takenHeroes")
    public Set<String> selectHero(String heroJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(heroJson);
        String hero = node.get("hero").asText();

        // Save hero to in-memory set
        takenHeroes.add(hero);

        // Return full list so all clients stay in sync
        return takenHeroes;
    }

    @MessageMapping("/getTakenHeroes")
    @SendTo("/topic/takenHeroes")
    public Set<String> getTakenHeroes() {
        return takenHeroes;
    }
}
