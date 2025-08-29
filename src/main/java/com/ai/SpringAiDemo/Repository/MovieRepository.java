package com.ai.SpringAiDemo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.SpringAiDemo.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	List<Movie> findByTitleContainingIgnoreCase(String title);
	List<Movie> findByGenreIgnoreCase(String genre);

}
