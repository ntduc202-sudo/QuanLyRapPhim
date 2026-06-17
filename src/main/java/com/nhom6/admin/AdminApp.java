package com.nhom6.admin;

import javax.swing.*;
import java.awt.*;

public class AdminApp extends JFrame {
    public AdminApp() {
        setTitle("Admin - Quan Ly Rap Phim");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton movieButton = new JButton("Quan ly phim");
        JButton showTimeButton = new JButton("Quan ly suat chieu");
        JButton ticketButton = new JButton("Xem danh sach ve");
        JButton exitButton = new JButton("Thoat");

        add(movieButton);
        add(showTimeButton);
        add(ticketButton);
        add(exitButton);

        movieButton.addActionListener(e -> new MovieManagementFrame().setVisible(true));
        showTimeButton.addActionListener(e -> new ShowTimeManagementFrame().setVisible(true)) ;
        ticketButton.addActionListener(e -> new TicketManagementFrame().setVisible(true));
        exitButton.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminApp().setVisible(true));
    }
}