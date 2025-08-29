package com.ai.SpringAiDemo;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomService {

    // roomId -> list of players (sessionIds)
    private final Map<String, List<String>> roomPlayers = new ConcurrentHashMap<>();

    // roomId -> (playerId -> selected heroes)
    private final Map<String, Map<String, List<Hero>>> roomHeroes = new ConcurrentHashMap<>();

    /** Add player to a room */
    public void addPlayer(String roomId, String playerId) {
        roomPlayers.computeIfAbsent(roomId, k -> Collections.synchronizedList(new ArrayList<>()));

        List<String> players = roomPlayers.get(roomId);
        synchronized (players) {
            if (!players.contains(playerId)) {
                players.add(playerId);
            }
        }
    }

    /** Get players in a room */
    public List<String> getPlayers(String roomId) {
        return roomPlayers.getOrDefault(roomId, Collections.emptyList());
    }

    /** Store selected heroes for a player */
    public void storeHeroes(String roomId, String playerId, List<Hero> heroes) {
        roomHeroes
                .computeIfAbsent(roomId, k -> new ConcurrentHashMap<>())
                .put(playerId, heroes);
    }

    /** Get heroes of a player in a room */
    public List<Hero> getHeroes(String roomId, String playerId) {
        return roomHeroes.getOrDefault(roomId, Collections.emptyMap())
                         .getOrDefault(playerId, Collections.emptyList());
    }

    /** Check if both players in a room have selected heroes */
    public boolean bothPlayersReady(String roomId) {
        Map<String, List<Hero>> heroesMap = roomHeroes.get(roomId);
        return heroesMap != null && heroesMap.size() == 2;
    }

    /** Remove player when disconnected */
    public List<String> removePlayer(String playerId) {
        List<String> affectedRooms = new ArrayList<>();

        for (String roomId : new HashSet<>(roomPlayers.keySet())) {
            List<String> players = roomPlayers.get(roomId);
            synchronized (players) {
                if (players.remove(playerId)) {
                    affectedRooms.add(roomId);
                }
            }

            // also remove heroes
            Map<String, List<Hero>> heroes = roomHeroes.get(roomId);
            if (heroes != null) {
                heroes.remove(playerId);
            }
        }
        return affectedRooms;
    }
}
