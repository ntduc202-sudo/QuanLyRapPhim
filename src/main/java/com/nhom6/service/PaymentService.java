package com.nhom6.service;

import com.nhom6.model.Payment;
import com.nhom6.model.PaymentStatus;
import com.nhom6.payment.PaymentMethod;

public class PaymentService {
    public boolean processPayment(Payment payment, PaymentMethod paymentMethod) {
        if (payment == null || paymentMethod == null) {
            return false;
        }

        if (payment.getAmount() <= 0) {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            return false;
        }

        boolean success = paymentMethod.pay(payment.getAmount());

        if (success) {
            payment.setPaymentStatus(PaymentStatus.PAID);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }

        return success;
    }
}