package com.nhom6.utils;

public class IdGenerator {
    private static int ticketCount = 1;
    private static int bookingCount = 1;

    public static String generateTicketId() {
        return String.format("TICKET%03d", ticketCount++);
    }

    public static String generateBookingId() {
        return String.format("BOOK%03d", bookingCount++);
    }
}