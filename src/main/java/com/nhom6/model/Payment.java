package com.nhom6.model;

public class Payment {
    private String paymentId;
    private double amount;
    private PaymentStatus paymentStatus;

    public Payment(String paymentId, double amount, PaymentStatus paymentStatus) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}