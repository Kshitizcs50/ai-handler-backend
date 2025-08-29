package com.ai.SpringAiDemo.MarvelService;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarvelService {

    private final String TMDB_API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZTFmZThkNDlkNzIzOGFhMWJkNTU4YmZiMDA0Y2Y1NSIsIm5iZiI6MTcwNTg3MTA2MC4yNzIsInN1YiI6IjY1YWQ4NmQ0ZDEwMGI2MDBhZGE1OTBjMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3a0-KLfLnN1DFSRdOHosvtgIr3c2eXkaavsI4-bbtLo"; // set in application.properties ideally
    private final String TMDB_UPCOMING_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + TMDB_API_KEY + "&language=en-US&page=1";

    public List<MarvelProject> getUpcomingMarvelProjects() {
        RestTemplate restTemplate = new RestTemplate();
        List<MarvelProject> marvelProjects = new ArrayList<>();
        try {
            String response = restTemplate.getForObject(TMDB_UPCOMING_URL, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode results = root.get("results");

            for (JsonNode movie : results) {
                String title = movie.get("title").asText();
                // Filter for Marvel-specific titles or MCU keywords
                if (title.toLowerCase().contains("marvel") || title.toLowerCase().contains("captain") || title.toLowerCase().contains("thor")) {
                    String poster = movie.get("poster_path").asText();
                    String releaseDate = movie.get("release_date").asText();
                    marvelProjects.add(new MarvelProject(title, poster, releaseDate));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marvelProjects;
    }
}

