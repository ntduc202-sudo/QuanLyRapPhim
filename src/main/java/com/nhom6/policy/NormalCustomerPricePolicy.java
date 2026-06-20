package com.nhom6.policy;

public class NormalCustomerPricePolicy implements TicketPricePolicy {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice;
    }
}