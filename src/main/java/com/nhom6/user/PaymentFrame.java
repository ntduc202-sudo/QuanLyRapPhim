package com.nhom6.user;

import com.nhom6.payment.*;
import com.nhom6.service.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PaymentFrame extends JFrame {
    private TicketService ticketService = new TicketService();
    private PaymentService paymentService = new PaymentService();

    private List<String> tickets;

    private DefaultListModel<String> ticketListModel = new DefaultListModel<>();
    private JList<String> ticketList = new JList<>(ticketListModel);

    private JTextArea ticketInfoArea = new JTextArea();

    private JComboBox<PaymentMethod> methodBox = new JComboBox<>(new PaymentMethod[]{
            new CashPayment(),
            new BankTransferPayment(),
            new EWalletPayment()
    });

    public PaymentFrame() {
        setTitle("Thanh Toan Ve");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        ticketInfoArea.setEditable(false);

        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.add(new JScrollPane(ticketInfoArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton payButton = new JButton("Thanh toan");

        bottomPanel.add(methodBox);
        bottomPanel.add(payButton);

        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        ticketList.setPreferredSize(new Dimension(300, 450));

        add(new JScrollPane(ticketList), BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        loadUnpaidTickets();

        ticketList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedTicket();
            }
        });

        payButton.addActionListener(e -> payTicket());
    }

    private void loadUnpaidTickets() {
        tickets = ticketService.getAllTicketLines();
        ticketListModel.clear();
        ticketInfoArea.setText("");

        for (String ticket : tickets) {
            String[] p = ticket.split(";");

            if (p.length == 11 && p[10].equalsIgnoreCase("UNPAID")) {
                ticketListModel.addElement(p[0] + " | " + p[5] + " | Ghe " + p[8] + " | " + p[9] + " VND");
            }
        }

        if (ticketListModel.isEmpty()) {
            ticketListModel.addElement("Khong co ve nao can thanh toan");
        }
    }

    private void showSelectedTicket() {
        int index = getRealTicketIndex();

        if (index == -1) {
            ticketInfoArea.setText("");
            return;
        }

        String[] p = tickets.get(index).split(";");

        ticketInfoArea.setText("Ma ve: " + p[0] + "\nKhach hang: " + p[1] + "\nSDT: " + p[2] + "\nEmail: " + p[3] + "\nMa phim: " + p[4] + "\nTen phim: " + p[5] + "\nMa suat chieu: " + p[6] + "\nThoi gian: " + p[7] + "\nGhe: " + p[8] + "\nGia ve: " + p[9] + " VND" + "\nTrang thai: " + p[10]);
    }

    private void payTicket() {
        int index = getRealTicketIndex();

        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Hay chon ve can thanh toan");
            return;
        }

        PaymentMethod method = (PaymentMethod) methodBox.getSelectedItem();

        if (method == null) {
            JOptionPane.showMessageDialog(this, "Hay chon phuong thuc thanh toan");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Xac nhan thanh toan bang: " + method.getName() + "?", "Xac nhan",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = paymentService.payTicket(tickets, index, method);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thanh toan thanh cong");
            loadUnpaidTickets();
        } else {
            JOptionPane.showMessageDialog(this, "Thanh toan that bai");
        }
    }

    private int getRealTicketIndex() {
        int selectedIndex = ticketList.getSelectedIndex();

        if (selectedIndex < 0) {
            return -1;
        }

        String selectedValue = ticketList.getSelectedValue();

        if (selectedValue == null ||
                selectedValue.equals("Khong co ve nao can thanh toan")) {
            return -1;
        }

        String ticketId = selectedValue.split("\\|")[0].trim();

        for (int i = 0; i < tickets.size(); i++) {
            String[] p = tickets.get(i).split(";");

            if (p.length == 11 && p[0].equalsIgnoreCase(ticketId)) {
                return i;
            }
        }

        return -1;
    }
}