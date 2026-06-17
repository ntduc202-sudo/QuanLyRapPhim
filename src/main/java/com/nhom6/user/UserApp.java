package com.nhom6.user;

import javax.swing.*;
import java.awt.*;

public class UserApp extends JFrame {

    public UserApp() {
        setTitle("User - Dat Ve Xem Phim");
        setSize(420, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("HE THONG DAT VE XEM PHIM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        JButton registerCustomerButton = new JButton("Dang ky khach hang");
        JButton ticketHistoryButton = new JButton("Xem ve da dat");
        JButton viewMovieButton = new JButton("Xem phim dang chieu");
        JButton searchMovieButton = new JButton("Tim phim");
        JButton bookingButton = new JButton("Dat ve");
        JButton paymentButton = new JButton("Thanh toan");
        JButton exitButton = new JButton("Thoat");

        buttonPanel.add(registerCustomerButton);
        buttonPanel.add(viewMovieButton);
        buttonPanel.add(searchMovieButton);
        buttonPanel.add(bookingButton);
        buttonPanel.add(ticketHistoryButton);
        buttonPanel.add(paymentButton);
        buttonPanel.add(exitButton);

        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        registerCustomerButton.addActionListener(e -> new RegisterCustomerFrame().setVisible(true));
        viewMovieButton.addActionListener(e -> new MovieListFrame().setVisible(true));
        searchMovieButton.addActionListener(e -> new SearchMovieFrame().setVisible(true));
        bookingButton.addActionListener(e -> new BookingFrame().setVisible(true));
        ticketHistoryButton.addActionListener(e -> new UserTicketFrame().setVisible(true));
        paymentButton.addActionListener(e -> new PaymentFrame().setVisible(true));
        exitButton.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserApp().setVisible(true));
    }
}