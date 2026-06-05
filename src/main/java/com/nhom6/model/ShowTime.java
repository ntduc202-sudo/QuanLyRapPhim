package com.nhom6.model;

public class ShowTime {
    private String showTimeId;
    private String movieId;
    private String showDate;
    private String showTime;
    private String room;

    public ShowTime(String showTimeId, String movieId, String showDate, String showTime, String room) {
        this.showTimeId = showTimeId;
        this.movieId = movieId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.room = room;
    }

    public String getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(String showTimeId) {
        this.showTimeId = showTimeId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "ShowTime{" +
                "showTimeId='" + showTimeId + '\'' +
                ", movieId='" + movieId + '\'' +
                ", showDate='" + showDate + '\'' +
                ", showTime='" + showTime + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
