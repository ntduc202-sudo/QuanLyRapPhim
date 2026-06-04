package com.nhom6.model;

public class Movie {
    private String  movieId;
    private String movieName;
    private String gerne;
    private String duration;
    private String ageLimit;
    private String status;

    public Movie(String movieId, String movieName, String gerne, String duration, String ageLimit, String status) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.gerne = gerne;
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

    public String getGerne() {
        return gerne;
    }

    public void setGerne(String gerne) {
        this.gerne = gerne;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(String ageLimit) {
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
                ", gerne='" + gerne + '\'' +
                ", duration='" + duration + '\'' +
                ", ageLimit='" + ageLimit + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
