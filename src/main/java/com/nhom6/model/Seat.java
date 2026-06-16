package com.nhom6.model;

public class Seat {
    private String seatId;
    private String seatNumber;
    private SeatType seatType;
    private SeatStatus seatStatus;

    public Seat(String seatId, String seatNumber, SeatType seatType, SeatStatus seatStatus) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.seatStatus = seatStatus;
    }

    public String getSeatId() {
        return seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }
}