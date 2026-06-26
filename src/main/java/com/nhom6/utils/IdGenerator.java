package com.nhom6.utils;

public class IdGenerator {

    public static String generateTicketId() {
        return "TICKET" + System.currentTimeMillis();
    }
}