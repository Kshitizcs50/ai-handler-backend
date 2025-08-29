package com.ai.SpringAiDemo;

import java.util.List;

public class SelectHeroesRequest {
    private String roomId;
    private List<Long> heroes;

    public String getRoomId() {
        return roomId;
    }
    public String getRoomId1() { return roomId; }
    public void setRoomId1(String roomId) { this.roomId = roomId; }

    public List<Long> getHeroes1() { return heroes; }
    public void setHeroes1(List<Long> heroes) { this.heroes = heroes; }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<Long> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Long> heroes) {
        this.heroes = heroes;
    }
}
