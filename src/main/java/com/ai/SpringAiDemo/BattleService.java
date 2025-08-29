package com.ai.SpringAiDemo;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class BattleService {

    // --- 1v1 fight ---
    public static BattleResult fight(Hero h1, Hero h2) {
        List<String> battleLog = new ArrayList<>();
        int h1Wins = 0, h2Wins = 0;

        // Compare Strength
        if (h1.getStrength() > h2.getStrength()) {
            h1Wins++;
            battleLog.add(h1.getName() + "'s Strength > " + h2.getName());
        } else if (h1.getStrength() < h2.getStrength()) {
            h2Wins++;
            battleLog.add(h2.getName() + "'s Strength > " + h1.getName());
        }

        // Compare Speed
        if (h1.getSpeed() > h2.getSpeed()) {
            h1Wins++;
            battleLog.add(h1.getName() + "'s Speed > " + h2.getName());
        } else if (h1.getSpeed() < h2.getSpeed()) {
            h2Wins++;
            battleLog.add(h2.getName() + "'s Speed > " + h1.getName());
        }

        // Compare Intelligence
        if (h1.getIntelligence() > h2.getIntelligence()) {
            h1Wins++;
            battleLog.add(h1.getName() + "'s Intelligence > " + h2.getName());
        } else if (h1.getIntelligence() < h2.getIntelligence()) {
            h2Wins++;
            battleLog.add(h2.getName() + "'s Intelligence > " + h1.getName());
        }

        Hero winner = null;
        if (h1Wins >= 2) winner = h1;
        else if (h2Wins >= 2) winner = h2;

        return new BattleResult(h1, h2, winner, String.join("\n", battleLog));
    }

    // --- Team fight (1v1 in rounds) ---
    public static TeamBattleResult teamFight(List<Hero> team1, List<Hero> team2) {
        List<String> battleLog = new ArrayList<>();
        int team1Wins = 0, team2Wins = 0;
        Map<Long, Integer> heroScores = new HashMap<>();

        int rounds = Math.min(team1.size(), team2.size());

        for (int i = 0; i < rounds; i++) {
            Hero h1 = team1.get(i);
            Hero h2 = team2.get(i);

            battleLog.add("⚔️ " + h1.getName() + " vs " + h2.getName());

            BattleResult roundResult = fight(h1, h2);
            battleLog.add(roundResult.getDescription());

            Hero roundWinner = roundResult.getWinner();
            if (roundWinner != null) {
                if (roundWinner.equals(h1)) team1Wins++;
                else team2Wins++;

                heroScores.put(roundWinner.getId(), heroScores.getOrDefault(roundWinner.getId(), 0) + 1);
            }
        }

        String finalWinner;
        List<Hero> winningTeam;

        if (team1Wins > team2Wins) {
            finalWinner = "Team 1";
            winningTeam = team1;
        } else if (team2Wins > team1Wins) {
            finalWinner = "Team 2";
            winningTeam = team2;
        } else {
            finalWinner = "Draw";
            winningTeam = Collections.emptyList();
        }

        // Find MVP
        Long mvpId = heroScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        Hero mvp = null;
        if (mvpId != null) {
            mvp = team1.stream()
                    .filter(h -> h.getId().equals(mvpId))
                    .findFirst()
                    .orElseGet(() -> team2.stream()
                            .filter(h -> h.getId().equals(mvpId))
                            .findFirst()
                            .orElse(null));
        }

        // Build result
        TeamBattleResult result = new TeamBattleResult(team1, team2, winningTeam, finalWinner);
        result.setDescription(String.join("\n", battleLog));
        result.setMvp(mvp);
        return result;
    }

}
