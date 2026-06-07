package com.nhom6.payment;

public class CashPayment implements PaymentMethod {

    @Override
    public boolean pay(double amount) {

        if (amount <= 0) {
            System.out.println("Số tiền thanh toán không hợp lệ!");
            return false;
        }

        System.out.println("Thanh toán tiền mặt thành công.");
        System.out.println("Số tiền: " + amount + " VNĐ");

        return true;
    }
}