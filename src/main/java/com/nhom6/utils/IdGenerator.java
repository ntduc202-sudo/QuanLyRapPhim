package com.nhom6.utils;

public class IdGenerator {
    private static int ticketCounter = 1;

    public static String generateTicketId() {
        return "TICKET" + System.currentTimeMillis();
    }
}