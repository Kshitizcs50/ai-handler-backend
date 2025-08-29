package com.ai.SpringAiDemo.MarvelService;



public class MarvelProject {
    private String title;
    private String posterPath;
    private String releaseDate;

    public MarvelProject(String title, String posterPath, String releaseDate) {
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
    }

    // getters and setters
    public String getTitle() { return title; }
    public String getPosterPath() { return posterPath; }
    public String getReleaseDate() { return releaseDate; }
}

