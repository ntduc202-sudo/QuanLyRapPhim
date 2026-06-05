package com.nhom6.model;

public class Movie {
    private String  movieId;
    private String movieName;
    private String genre;
    private int duration;
    private int ageLimit;
    private String status;

    public Movie(String movieId, String movieName, String genre, int duration, int ageLimit, String status) {
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

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId='" + movieId + '\'' +
                ", movieName='" + movieName + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", ageLimit=" + ageLimit +
                ", status='" + status + '\'' +
                '}';
    }
}
