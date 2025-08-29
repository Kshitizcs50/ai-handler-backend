package com.ai.SpringAiDemo;

public class BattleResult {
    private Hero hero1;
    private Hero hero2;
    private Hero winner;
    private String description;

    public BattleResult(Hero hero1, Hero hero2, Hero winner, String description) {
        this.setHero1(hero1);
        this.setHero2(hero2);
        this.setWinner(winner);
        this.setDescription(description);
    }

	public Hero getHero1() {
		return hero1;
	}

	public void setHero1(Hero hero1) {
		this.hero1 = hero1;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Hero getHero2() {
		return hero2;
	}

	public void setHero2(Hero hero2) {
		this.hero2 = hero2;
	}

	public Hero getWinner() {
		return winner;
	}

	public void setWinner(Hero winner) {
		this.winner = winner;
	}

    // getters and setters
}

