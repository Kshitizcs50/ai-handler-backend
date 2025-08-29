package com.ai.SpringAiDemo;

import java.util.List;

public class TeamBattleResult {
    private String description1;
    private String finalWinner;
    private List<Hero> winners;
    private Hero mvp;
    private List<Hero> team1;
    private List<Hero> team2;
    private List<Hero> winningTeam;
    private String description;

    public TeamBattleResult(List<Hero> team1, List<Hero> team2, List<Hero> winningTeam, String description) {
        this.team1 = team1;
        this.team2 = team2;
        this.winningTeam = winningTeam;
        this.description1 = description;
    }

    // --- Getters ---
    public List<Hero> getTeam1() { return team1; }
    public List<Hero> getTeam2() { return team2; }
    public List<Hero> getWinningTeam() { return winningTeam; }
    public String getDescription1() { return description1; }

    // Getters & Setters
    public String getDescription() { return description1; }
    public void setDescription(String description) { this.description1 = description; }

    public String getFinalWinner() { return finalWinner; }
    public void setFinalWinner(String finalWinner) { this.finalWinner = finalWinner; }

    public List<Hero> getWinners() { return winners; }
    public void setWinners(List<Hero> winners) { this.winners = winners; }

    public Hero getMvp() { return mvp; }
    public void setMvp(Hero mvp) { this.mvp = mvp; }
    
}
