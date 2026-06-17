package com.nhom6.model;

public class Ticket {
    private String ticketId;
    private Customer customer;
    private Movie movie;
    private ShowTime showTime;
    private Seat seat;
    private double finalPrice;
    private PaymentStatus paymentStatus;

    public Ticket(String ticketId, Customer customer, Movie movie, ShowTime showTime, Seat seat, double finalPrice, PaymentStatus paymentStatus) {
        this.ticketId = ticketId;
        this.customer = customer;
        this.movie = movie;
        this.showTime = showTime;
        this.seat = seat;
        this.finalPrice = finalPrice;
        this.paymentStatus = paymentStatus;
    }

    public String toFileString() {
        return ticketId + ";" +
                customer.getFullName() + ";" +
                customer.getPhone() + ";" +
                customer.getEmail() + ";" +
                movie.getMovieId() + ";" +
                movie.getMovieName() + ";" +
                showTime.getShowTimeId() + ";" +
                showTime.getShowTime() + "-" + showTime.getEndTime() + ";" +
                seat.getSeatNumber() + ";" +
                finalPrice + ";" +
                paymentStatus;
    }

    @Override
    public String toString() {
        return ticketId + " - " + movie.getMovieName() + " - Ghe " + seat.getSeatNumber() + " - " + finalPrice + " VND";
    }
}