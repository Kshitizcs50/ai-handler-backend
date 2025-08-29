package com.ai.SpringAiDemo;

import java.util.List;

public class TeamFightRequest {
    private List<Long> team1;
    private List<Long> team2;

    public List<Long> getTeam1() {
        return team1;
    }
    public void setTeam1(List<Long> team1) {
        this.team1 = team1;
    }
    public List<Long> getTeam2() {
        return team2;
    }
    public void setTeam2(List<Long> team2) {
        this.team2 = team2;
    }
}
