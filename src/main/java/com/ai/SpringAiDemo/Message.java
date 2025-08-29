package com.ai.SpringAiDemo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;    // "CHAT" or "USER_TAKEN"
    private String sender;  // for chat
    private String text;    // for chat
    private String hero;    // store hero name here
    private String content;
    private Long heroId;

    public Message(String string, String hero2, Object object, boolean b) {
		// TODO Auto-generated constructor stub
	}
	// getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getHero() { return hero; }
    public void setHero(String hero) { this.hero = hero; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
	public Long getHeroId() {
		// TODO Auto-generated method stub
		return heroId;
	}
	 public void setHeroId(Long heroId) { this.heroId = heroId; }
	}

