package com.nhom6.service;

import com.nhom6.model.*;
import com.nhom6.policy.*;
import com.nhom6.utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class BookingService {

    public Ticket bookTicket(Customer customer, Movie movie, ShowTime showTime, Seat seat) {
        if (customer == null || movie == null || showTime == null || seat == null) {
            return null;
        }

        if (movie.getStatus() != MovieStatus.DANG_CHIEU) {
            return null;
        }

        if (seat.getSeatStatus() == SeatStatus.BOOKED) {
            return null;
        }

        double finalPrice = calculateFinalPrice(
                showTime.getBasePrice(),
                seat.getSeatType(),
                customer.getCustomerType()
        );

        seat.setSeatStatus(SeatStatus.BOOKED);

        Ticket ticket = new Ticket(
                IdGenerator.generateTicketId(),
                customer,
                movie,
                showTime,
                seat,
                finalPrice,
                PaymentStatus.UNPAID
        );
        return ticket;
    }

    private double calculateFinalPrice(
            double basePrice,
            SeatType seatType,
            CustomerType customerType) {

        double price = basePrice;

        if (seatType == SeatType.VIP) {
            price += 30000;
        }

        if (seatType == SeatType.COUPLE) {
            price += 50000;
        }

        TicketPricePolicy policy = getPolicy(customerType);

        return policy.calculatePrice(price);
    }
    private TicketPricePolicy getPolicy(CustomerType customerType) {

        switch (customerType) {

            case STUDENT:
                return new StudentPricePolicy();

            case VIP:
                return new VipPricePolicy();

            default:
                return new NormalCustomerPricePolicy();
        }
    }
}