package com.nhom6.service;

import com.nhom6.model.*;
import com.nhom6.utils.IdGenerator;

public class BookingService {
    public Ticket bookTicket(Customer customer, Movie movie, ShowTime showTime, Seat seat) {
        if (customer == null || movie == null || showTime == null || seat == null) {
            return null;
        }

        if (movie.getStatus() != MovieStatus.DANG_CHIEU) {
            return null;
        }

        double finalPrice = calculateFinalPrice(showTime.getBasePrice(), seat.getSeatType(), customer.getCustomerType());

        return new Ticket(
                IdGenerator.generateTicketId(),
                customer,
                movie,
                showTime,
                seat,
                finalPrice,
                PaymentStatus.UNPAID
        );
    }

    private double calculateFinalPrice(double basePrice, SeatType seatType, CustomerType customerType) {
        double price = basePrice;

        if (seatType == SeatType.VIP) {
            price += 30000;
        }

        if (seatType == SeatType.COUPLE) {
            price += 50000;
        }

        if (customerType == CustomerType.STUDENT) {
            price *= 0.9;
        }

        if (customerType == CustomerType.VIP) {
            price *= 0.8;
        }

        return price;
    }
}