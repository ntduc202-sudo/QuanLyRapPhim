package com.nhom6.controller;

import com.nhom6.model.Movie;
import com.nhom6.service.MovieService;

import java.util.List;

public class MovieController {
    private MovieService movieService = new MovieService();

    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

}