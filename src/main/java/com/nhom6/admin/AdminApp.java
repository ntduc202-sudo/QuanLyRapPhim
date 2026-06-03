package com.nhom6.admin;

import javax.swing.*;
import java.awt.*;

public class AdminApp extends JFrame {
    public AdminApp() {
        setTitle("Admin - Quan Ly Rap Phim");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton movieButton = new JButton("Quan ly phim");
        JButton roomButton = new JButton("Quan ly phong chieu");
        JButton showtimeButton = new JButton("Quan ly suat chieu");
        JButton ticketButton = new JButton("Xem danh sach ve");
        JButton exitButton = new JButton("Thoat");

        add(movieButton);
        add(roomButton);
        add(showtimeButton);
        add(ticketButton);
        add(exitButton);

        movieButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Goi MovieService"));
        roomButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Goi CinemaRoomService"));
        showtimeButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Goi ShowtimeService"));
        ticketButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Goi BookingService/FileRepository"));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminApp().setVisible(true));
    }
}