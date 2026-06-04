package com.nhom6.repository;

import com.nhom6.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private final List<Movie> movies = new ArrayList<>();

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void deleteMovie(String movieId) {
        movies.removeIf(movie ->
                movie.getMovieId().equalsIgnoreCase(movieId));
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public Movie findMovieById(String movieId) {
        for (Movie movie : movies) {
            if (movie.getMovieId().equalsIgnoreCase(movieId)) {
                return movie;
            }
        }
        return null;
    }
}