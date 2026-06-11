package com.nhom6.model;

public class ShowTime {
    private String showTimeId;
    private String movieId;
    private String showDate;
    private String startTime;
    private String endTime;
    private String roomId;
    private double basePrice;

    public ShowTime(String showTimeId, String movieId, String showDate, String startTime, String endTime, String roomId, double basePrice) {
        this.showTimeId = showTimeId;
        this.movieId = movieId;
        this.showDate = showDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomId = roomId;
        this.basePrice = basePrice;
    }

    public String getShowTimeId() {
        return showTimeId;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getShowDate() {
        return showDate;
    }

    public String getShowTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getRoom() {
        return roomId;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        return showTimeId + " | " + startTime + "-" + endTime;
    }
}