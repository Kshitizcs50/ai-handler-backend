package com.ai.SpringAiDemo.MarvelService;



import com.ai.SpringAiDemo.MarvelService.MarvelProject;
import com.ai.SpringAiDemo.MarvelService.MarvelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MarvelController {

    private final MarvelService marvelService;

    public MarvelController(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    @GetMapping("/api/marvel")
    public List<MarvelProject> getMarvelProjects() {
        return marvelService.getUpcomingMarvelProjects();
    }
}

