package com.nhom6.service;

import com.nhom6.model.Ticket;
import com.nhom6.repository.FileRepository;

import java.util.List;

public class TicketService {
    private FileRepository fileRepository = new FileRepository();

    public void saveTicket(Ticket ticket) {
        fileRepository.saveTicket(ticket);
    }

    public List<String> getAllTicketLines() {
        return fileRepository.loadTicketLines();
    }

    public void saveAllTicketLines(List<String> tickets) {
        fileRepository.saveAllTicketLines(tickets);
    }

    public String getTicketsAsText() {
        return fileRepository.loadTicketsAsText();
    }
}