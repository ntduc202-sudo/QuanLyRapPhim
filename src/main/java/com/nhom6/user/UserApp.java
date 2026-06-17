package com.nhom6.user;

import javax.swing.*;
import java.awt.*;

public class UserApp extends JFrame {
    public UserApp() {
        setTitle("User - Dat Ve Xem Phim");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        JButton registerCustomerButton = new JButton("Dang ky khach hang");
        JButton viewMovieButton = new JButton("Xem phim");
        JButton bookingButton = new JButton("Dat ve");
        JButton ticketButton = new JButton("Xem ve da dat");
        JButton paymentButton = new JButton("Thanh toan");
        JButton exitButton = new JButton("Thoat");

        add(registerCustomerButton);
        add(viewMovieButton);
        add(bookingButton);
        add(ticketButton);
        add(paymentButton);
        add(exitButton);

        registerCustomerButton.addActionListener(e -> new RegisterCustomerFrame().setVisible(true));
        viewMovieButton.addActionListener(e -> new SearchMovieFrame().setVisible(true));
        bookingButton.addActionListener(e -> new BookingFrame().setVisible(true));
        paymentButton.addActionListener(e -> new PaymentFrame().setVisible(true));
        ticketButton.addActionListener(e -> new UserTicketFrame().setVisible(true));
        exitButton.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserApp().setVisible(true));
    }
}