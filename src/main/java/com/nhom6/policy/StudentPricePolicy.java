package com.nhom6.policy;

public class StudentPricePolicy implements TicketPricePolicy {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 0.9;
    }
}