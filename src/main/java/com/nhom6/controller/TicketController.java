package com.nhom6.controller;

import com.nhom6.model.Ticket;
import com.nhom6.service.TicketService;

import java.util.List;

public class TicketController {
    private TicketService ticketService = new TicketService();

    public List<String> getAllTicketLines() {
        return ticketService.getAllTicketLines();
    }

}