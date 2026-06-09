package com.nhom6.payment;

public class BankTransferPayment implements PaymentMethod {

    @Override
    public boolean pay(double amount) {
        return true;
    }

    @Override
    public String getName() {
        return "Chuyen Khoan";
    }
}