package com.nhom6.admin;

import javax.swing.*;
import java.awt.*;

public class AdminApp extends JFrame {
    public AdminApp() {
        setTitle("Admin - Quan Ly Rap Phim");
        setSize(420, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("ADMIN - QUAN LY RAP PHIM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton movieButton = new JButton("Quan ly phim");
        JButton showTimeButton = new JButton("Quan ly suat chieu");
        JButton ticketButton = new JButton("Xem danh sach ve");
        JButton exitButton = new JButton("Thoat");

        buttonPanel.add(movieButton);
        buttonPanel.add(showTimeButton);
        buttonPanel.add(ticketButton);
        buttonPanel.add(exitButton);

        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        movieButton.addActionListener(e -> new MovieManagementFrame().setVisible(true));
        showTimeButton.addActionListener(e -> new ShowTimeManagementFrame().setVisible(true));
        ticketButton.addActionListener(e -> new TicketManagementFrame().setVisible(true));
        exitButton.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminApp().setVisible(true));
    }
}