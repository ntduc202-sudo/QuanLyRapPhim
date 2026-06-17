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

    public void updateMovie(Movie oldMovie, Movie newMovie) {
        List<Movie> movies = fileRepository.loadMovies();

        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getMovieId().equalsIgnoreCase(oldMovie.getMovieId())) {
                movies.set(i, newMovie);
                break;
            }
        }

        fileRepository.saveAllMovies(movies);
    }

    public void deleteMovie(Movie movie) {
        List<Movie> movies = fileRepository.loadMovies();
        movies.removeIf(m -> m.getMovieId().equalsIgnoreCase(movie.getMovieId()));
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

    public boolean isDuplicateMovieIdForUpdate(String movieId, Movie selectedMovie) {
        for (Movie movie : fileRepository.loadMovies()) {
            if (!movie.getMovieId().equalsIgnoreCase(selectedMovie.getMovieId())
                    && movie.getMovieId().equalsIgnoreCase(movieId)) {
                return true;
            }
        }
        return false;
    }
}