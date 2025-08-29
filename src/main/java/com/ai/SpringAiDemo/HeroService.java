package com.ai.SpringAiDemo;

import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class HeroService {
    private final Set<String> takenHeroes = new HashSet<>();

    public synchronized boolean takeHero(String hero) {
        if (takenHeroes.contains(hero)) {
            return false;
        }
        takenHeroes.add(hero);
        return true;
    }

    public synchronized Set<String> getTakenHeroes() {
        return takenHeroes;
    }
}

