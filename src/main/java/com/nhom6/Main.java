package com.nhom6;

import com.nhom6.payment.CashPayment;
import com.nhom6.payment.PaymentMethod;

public class Main {

    public static void main(String[] args) {

        PaymentMethod payment = new CashPayment();

        boolean result = payment.pay(100000);

        if (result) {
            System.out.println("Thanh toán thành công!");
        } else {
            System.out.println("Thanh toán thất bại!");
        }
    }
}