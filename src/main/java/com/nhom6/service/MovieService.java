package com.nhom6.service;

import com.nhom6.model.Movie;
import com.nhom6.repository.FileRepository;

import java.util.List;

public class MovieService {
    private FileRepository fileRepository = new FileRepository();

    public List<Movie> getAllMovies() {
        return fileRepository.loadMovies();
    }

    public void addMovie(Movie movie) {
        List<Movie> movies = fileRepository.loadMovies();
        movies.add(movie);
        fileRepository.saveAllMovies(movies);
    }

    public boolean isDuplicateMovieId(String movieId) {
        for (Movie movie : fileRepository.loadMovies()) {
            if (movie.getMovieId().equalsIgnoreCase(movieId)) {
                return true;
            }
        }
        return false;
    }
}