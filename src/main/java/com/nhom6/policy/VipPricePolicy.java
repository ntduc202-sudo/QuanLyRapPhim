package com.nhom6.policy;

public class VipPricePolicy implements TicketPricePolicy {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 0.8;
    }
}