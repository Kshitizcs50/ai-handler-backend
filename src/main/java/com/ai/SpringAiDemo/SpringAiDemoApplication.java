package com.ai.SpringAiDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
	    org.springframework.ai.model.openai.autoconfigure.OpenAiAudioSpeechAutoConfiguration.class
	})
public class SpringAiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiDemoApplication.class, args);
	}

}
