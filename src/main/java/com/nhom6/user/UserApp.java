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

        JButton registerButton = new JButton("Dang ky khach hang");
        JButton viewMovieButton = new JButton("Xem phim");
        JButton bookingButton = new JButton("Dat ve");
        JButton paymentButton = new JButton("Thanh toan");
        JButton exitButton = new JButton("Thoat");

        add(registerButton);
        add(viewMovieButton);
        add(bookingButton);
        add(paymentButton);
        add(exitButton);

        registerButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chuc nang khach hang se lam sau"));
        viewMovieButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chuc nang xem phim se lam sau"));
        bookingButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chuc nang dat ve se lam sau"));
        paymentButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chuc nang thanh toan se lam sau"));
        exitButton.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserApp().setVisible(true));
    }
}