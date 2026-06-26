package com.nhom6.service;

import com.nhom6.model.PaymentStatus;
import com.nhom6.payment.PaymentMethod;

import java.util.List;

public class PaymentService {
    private TicketService ticketService = new TicketService();

    public boolean payTicket(List<String> tickets, int index, PaymentMethod paymentMethod) {
        if (tickets == null || paymentMethod == null) {
            return false;
        }

        if (index < 0 || index >= tickets.size()) {
            return false;
        }

        String[] p = tickets.get(index).split(";");

        if (p.length != 11) {
            return false;
        }

        if (p[10].equalsIgnoreCase(PaymentStatus.PAID.name())) {
            return false;
        }

        double amount;

        try {
            amount = Double.parseDouble(p[9]);
        } catch (NumberFormatException e) {
            return false;
        }

        if (amount <= 0) {
            return false;
        }

        boolean success = paymentMethod.pay(amount);

        if (!success) {
            return false;
        }

        p[10] = PaymentStatus.PAID.name();
        tickets.set(index, String.join(";", p));
        ticketService.saveAllTicketLines(tickets);

        return true;
    }
}