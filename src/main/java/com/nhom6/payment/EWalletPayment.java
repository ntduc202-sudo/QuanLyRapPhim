package com.nhom6.payment;

public class EWalletPayment implements PaymentMethod {

    @Override
    public boolean pay(double amount) {
        return true;
    }

    @Override
    public String getName() {
        return "Vi Dien Tu";
    }
}