package com.ai.SpringAiDemo;

import org.springframework.web.bind.annotation.*;

import com.ai.SpringAiDemo.Repository.MovieRepository;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:3000") // allow Next.js
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }
    @GetMapping("/search")
    public List<Movie> searchMovies(@RequestParam String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    @GetMapping("/genre")
    public List<Movie> getMoviesByGenre(@RequestParam String genre) {
        return movieRepository.findByGenreIgnoreCase(genre);
    }

    @PostMapping("/{id}/rate")
    public Movie rateMovie(@PathVariable Long id, @RequestParam int rating) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        // simple avg rating logic (can be improved with users table)
        movie.setRating((movie.getRating() + rating) / 2);
        return movieRepository.save(movie);
    }

}
