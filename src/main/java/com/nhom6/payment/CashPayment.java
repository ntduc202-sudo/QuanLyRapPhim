package com.nhom6.payment;

public class CashPayment implements PaymentMethod {

    @Override
    public boolean pay(double amount) {
        return true;
    }

    @Override
    public String getName() {
        return "Tien Mat";
    }

    @Override
    public String toString() {
        return getName();
    }
}