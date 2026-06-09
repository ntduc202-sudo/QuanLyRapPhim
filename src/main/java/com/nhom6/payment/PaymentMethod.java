package com.nhom6.payment;

public interface PaymentMethod {

    boolean pay(double amount);

    String getName();
}