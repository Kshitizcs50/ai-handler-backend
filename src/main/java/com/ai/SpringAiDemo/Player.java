package com.ai.SpringAiDemo;

import java.util.List;

public class Player {
    private String sessionId;
    private List<Hero> selectedHeroes;

    public Player(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() { return sessionId; }
    public List<Hero> getSelectedHeroes() { return selectedHeroes; }
    public void setSelectedHeroes(List<Hero> selectedHeroes) { this.selectedHeroes = selectedHeroes; }
}
