package com.nhom6.admin;

import com.nhom6.service.TicketService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TicketManagementFrame extends JFrame {
    private TicketService ticketService = new TicketService();
    private List<String> tickets;

    private DefaultListModel<String> ticketListModel = new DefaultListModel<>();
    private JList<String> ticketList = new JList<>(ticketListModel);
    private JTextArea ticketDetailArea = new JTextArea();

    public TicketManagementFrame() {
        setTitle("Danh Sach Ve");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        ticketDetailArea.setEditable(false);
        ticketDetailArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        ticketList.setPreferredSize(new Dimension(220, 400));

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(ticketList),
                new JScrollPane(ticketDetailArea)
        );

        splitPane.setDividerLocation(220);

        JButton displayButton = new JButton("Hien thi danh sach ve");

        add(splitPane, BorderLayout.CENTER);
        add(displayButton, BorderLayout.SOUTH);

        loadTickets();

        ticketList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedTicket();
            }
        });

        displayButton.addActionListener(e -> loadTickets());
    }

    private void loadTickets() {
        tickets = ticketService.getAllTicketLines();
        ticketListModel.clear();
        ticketDetailArea.setText("");

        for (String ticket : tickets) {
            String[] p = ticket.split(";");

            if (p.length == 11) {
                ticketListModel.addElement(p[0]);
            }
        }

        if (ticketListModel.isEmpty()) {
            ticketListModel.addElement("Chua co ve nao");
        }
    }

    private void showSelectedTicket() {
        int index = ticketList.getSelectedIndex();

        if (index < 0 || tickets == null || tickets.isEmpty()) {
            return;
        }

        if (index >= tickets.size()) {
            return;
        }

        String[] p = tickets.get(index).split(";");

        if (p.length != 11) {
            return;
        }

        ticketDetailArea.setText(
                "Ma ve: " + p[0] +
                        "\nKhach hang: " + p[1] +
                        "\nSDT: " + p[2] +
                        "\nEmail: " + p[3] +
                        "\nMa phim: " + p[4] +
                        "\nTen phim: " + p[5] +
                        "\nMa suat chieu: " + p[6] +
                        "\nThoi gian: " + p[7] +
                        "\nGhe: " + p[8] +
                        "\nGia ve: " + p[9] + " VND" +
                        "\nTrang thai: " + p[10]
        );
    }
}