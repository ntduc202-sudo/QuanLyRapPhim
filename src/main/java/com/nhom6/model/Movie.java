package com.nhom6.model;

public class Movie {
    private String movieId;
    private String movieName;
    private String genre;
    private int duration;
    private int ageLimit;
    private MovieStatus status;

    public Movie(String movieId, String movieName, String genre, int duration, int ageLimit, MovieStatus status) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.genre = genre;
        this.duration = duration;
        this.ageLimit = ageLimit;
        this.status = status;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public MovieStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return movieId + " - " + movieName + " - " + status;
    }
}