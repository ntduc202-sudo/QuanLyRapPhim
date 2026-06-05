package com.nhom6.user;

import javax.swing.*;
import java.awt.*;

public class UserApp extends JFrame {
    public UserApp() {
        setTitle("User - Dat Ve Xem Phim");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton viewMovieButton = new JButton("Xem phim dang chieu");
        JButton searchMovieButton = new JButton("Tim phim");
        JButton bookingButton = new JButton("Dat ve");
        JButton paymentButton = new JButton("Thanh toan");
        JButton exitButton = new JButton("Thoat");

        add(viewMovieButton);
        add(searchMovieButton);
        add(bookingButton);
        add(paymentButton);
        add(exitButton);

        viewMovieButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Goi MovieService"));
        searchMovieButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Goi MovieService tim phim"));
        bookingButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Goi BookingService"));
        paymentButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Goi PaymentService"));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserApp().setVisible(true));
    }
}