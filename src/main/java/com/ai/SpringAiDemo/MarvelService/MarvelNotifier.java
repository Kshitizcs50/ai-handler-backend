package com.ai.SpringAiDemo.MarvelService;



import com.ai.SpringAiDemo.MarvelService.MarvelProject;
import com.ai.SpringAiDemo.MarvelService.MarvelService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarvelNotifier {

    private final SimpMessagingTemplate messagingTemplate;
    private final MarvelService marvelService;

    public MarvelNotifier(SimpMessagingTemplate messagingTemplate, MarvelService marvelService) {
        this.messagingTemplate = messagingTemplate;
        this.marvelService = marvelService;
    }

    // Push every 5 minutes
    @Scheduled(fixedRate = 300000)
    public void sendMarvelUpdates() {
        List<MarvelProject> projects = marvelService.getUpcomingMarvelProjects();
        messagingTemplate.convertAndSend("/topic/marvelUpdates", projects);
    }
}
