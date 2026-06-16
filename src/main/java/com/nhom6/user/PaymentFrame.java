package com.nhom6.user;

import com.nhom6.model.Payment;
import com.nhom6.model.PaymentStatus;
import com.nhom6.payment.*;
import com.nhom6.service.PaymentService;

import javax.swing.*;
import java.awt.*;

public class PaymentFrame extends JFrame {
    private PaymentService paymentService = new PaymentService();

    private JTextField paymentIdField = new JTextField();
    private JTextField amountField = new JTextField();
    private JComboBox<PaymentMethod> methodBox = new JComboBox<>(new PaymentMethod[]{
            new CashPayment(),
            new BankTransferPayment(),
            new EWalletPayment()
    });

    public PaymentFrame() {
        setTitle("Thanh Toan");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JButton payButton = new JButton("Thanh toan");

        add(new JLabel("Ma thanh toan"));
        add(paymentIdField);
        add(new JLabel("So tien"));
        add(amountField);
        add(new JLabel("Phuong thuc"));
        add(methodBox);
        add(new JLabel());
        add(payButton);

        payButton.addActionListener(e -> pay());
    }

    private void pay() {
        try {
            Payment payment = new Payment(
                    paymentIdField.getText().trim(),
                    Double.parseDouble(amountField.getText().trim()),
                    PaymentStatus.UNPAID
            );

            PaymentMethod method = (PaymentMethod) methodBox.getSelectedItem();

            if (paymentService.processPayment(payment, method)) {
                JOptionPane.showMessageDialog(this, "Thanh toan thanh cong");
            } else {
                JOptionPane.showMessageDialog(this, "Thanh toan that bai");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "So tien phai la so");
        }
    }
}