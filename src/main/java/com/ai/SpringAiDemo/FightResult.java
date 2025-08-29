package com.ai.SpringAiDemo;

import java.util.List;

public class FightResult {
    public List<String> description;
    public String finalWinner;
    public List<Hero> winners;
    public Hero mvp;

    public FightResult(List<String> description, String finalWinner, List<Hero> winners, Hero mvp){
        this.description = description;
        this.finalWinner = finalWinner;
        this.winners = winners;
        this.mvp = mvp;
    }
}

