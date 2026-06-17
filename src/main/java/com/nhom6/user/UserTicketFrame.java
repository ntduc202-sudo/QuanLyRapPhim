package com.nhom6.user;

import com.nhom6.model.Movie;
import com.nhom6.model.SeatType;
import com.nhom6.model.ShowTime;
import com.nhom6.service.MovieService;
import com.nhom6.service.SeatService;
import com.nhom6.service.ShowTimeService;
import com.nhom6.service.TicketService;
import com.nhom6.utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserTicketFrame extends JFrame {
    private MovieService movieService = new MovieService();
    private ShowTimeService showTimeService = new ShowTimeService();
    private SeatService seatService = new SeatService();
    private TicketService ticketService = new TicketService();

    private List<String> tickets;

    private DefaultListModel<String> ticketListModel = new DefaultListModel<>();
    private JList<String> ticketList = new JList<>(ticketListModel);

    private JTextField ticketIdField = new JTextField();
    private JTextField customerNameField = new JTextField();
    private JTextField phoneField = new JTextField();
    private JTextField emailField = new JTextField();

    private JComboBox<Movie> movieComboBox = new JComboBox<>();
    private JComboBox<ShowTime> showTimeComboBox = new JComboBox<>();
    private JComboBox<SeatType> seatTypeComboBox = new JComboBox<>(SeatType.values());
    private JComboBox<String> seatNumberComboBox = new JComboBox<>();

    private JTextField priceField = new JTextField();
    private JTextField statusField = new JTextField();

    public UserTicketFrame() {
        setTitle("Ve Da Dat");
        setSize(950, 620);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        ticketIdField.setEditable(false);
        priceField.setEditable(false);
        statusField.setEditable(false);

        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));

        formPanel.add(new JLabel("Ma ve"));
        formPanel.add(ticketIdField);
        formPanel.add(new JLabel("Ten khach hang"));
        formPanel.add(customerNameField);
        formPanel.add(new JLabel("So dien thoai"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phim"));
        formPanel.add(movieComboBox);
        formPanel.add(new JLabel("Suat chieu"));
        formPanel.add(showTimeComboBox);
        formPanel.add(new JLabel("Loai ghe"));
        formPanel.add(seatTypeComboBox);
        formPanel.add(new JLabel("Ma ghe"));
        formPanel.add(seatNumberComboBox);
        formPanel.add(new JLabel("Gia ve"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Trang thai"));
        formPanel.add(statusField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        JButton updateButton = new JButton("Luu chinh sua");
        JButton refreshButton = new JButton("Tai lai");
        JButton clearButton = new JButton("Xoa form");

        buttonPanel.add(updateButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(clearButton);

        ticketList.setPreferredSize(new Dimension(330, 500));

        add(new JScrollPane(ticketList), BorderLayout.WEST);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadMovies();
        loadTickets();

        ticketList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedTicket();
            }
        });

        movieComboBox.addActionListener(e -> {
            loadShowTimesBySelectedMovie();
            loadSeatNumbers();
            updatePrice();
        });

        showTimeComboBox.addActionListener(e -> {
            loadSeatNumbers();
            updatePrice();
        });

        seatTypeComboBox.addActionListener(e -> {
            loadSeatNumbers();
            updatePrice();
        });

        seatNumberComboBox.addActionListener(e -> updatePrice());

        updateButton.addActionListener(e -> updateTicket());
        refreshButton.addActionListener(e -> loadTickets());
        clearButton.addActionListener(e -> clearForm());
    }

    private void loadMovies() {
        movieComboBox.removeAllItems();

        for (Movie movie : movieService.getAllMovies()) {
            movieComboBox.addItem(movie);
        }
    }

    private void loadTickets() {
        tickets = ticketService.getAllTicketLines();
        ticketListModel.clear();

        for (String ticket : tickets) {
            String[] p = ticket.split(";");

            if (p.length == 11) {
                ticketListModel.addElement(p[0] + " | " + p[5] + " | Ghe " + p[8]);
            }
        }

        if (tickets.isEmpty()) {
            ticketListModel.addElement("Chua co ve nao duoc dat");
        }
    }

    private void showSelectedTicket() {
        int index = ticketList.getSelectedIndex();

        if (index < 0 || tickets.isEmpty()) {
            return;
        }

        String[] p = tickets.get(index).split(";");

        if (p.length != 11) {
            JOptionPane.showMessageDialog(this, "Du lieu ve khong dung dinh dang");
            return;
        }

        ticketIdField.setText(p[0]);
        customerNameField.setText(p[1]);
        phoneField.setText(p[2]);
        emailField.setText(p[3]);

        selectMovieById(p[4]);
        loadShowTimesBySelectedMovie();
        selectShowTimeById(p[6]);

        selectSeatTypeBySeatNumber(p[8]);
        loadSeatNumbers();
        seatNumberComboBox.setSelectedItem(p[8]);

        priceField.setText(p[9]);
        statusField.setText(p[10]);
    }

    private void loadShowTimesBySelectedMovie() {
        showTimeComboBox.removeAllItems();

        Movie movie = (Movie) movieComboBox.getSelectedItem();

        if (movie == null) {
            return;
        }

        for (ShowTime showTime : showTimeService.getShowTimesByMovieId(movie.getMovieId())) {
            showTimeComboBox.addItem(showTime);
        }
    }

    private void loadSeatNumbers() {
        seatNumberComboBox.removeAllItems();

        ShowTime showTime = (ShowTime) showTimeComboBox.getSelectedItem();
        SeatType seatType = (SeatType) seatTypeComboBox.getSelectedItem();

        if (showTime == null || seatType == null) {
            return;
        }

        int currentIndex = ticketList.getSelectedIndex();

        for (String seatNumber : seatService.getSeatNumbersByType(seatType, showTime.getShowTimeId())) {
            if (seatNumber.contains("DA DAT")) {
                String realSeat = seatNumber.replace(" - DA DAT", "");

                if (isCurrentTicketSeat(showTime.getShowTimeId(), realSeat, currentIndex)) {
                    seatNumberComboBox.addItem(realSeat);
                }
            } else {
                seatNumberComboBox.addItem(seatNumber);
            }
        }
    }

    private void updateTicket() {
        int index = ticketList.getSelectedIndex();

        if (index < 0 || tickets.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hay chon ve can sua");
            return;
        }

        String[] currentTicket = tickets.get(index).split(";");

        if (currentTicket.length == 11 && currentTicket[10].equalsIgnoreCase("PAID")) {
            JOptionPane.showMessageDialog(this, "Ve da thanh toan, khong the chinh sua");
            return;
        }

        if (!validateTicketForm()) {
            return;
        }

        Movie movie = (Movie) movieComboBox.getSelectedItem();
        ShowTime showTime = (ShowTime) showTimeComboBox.getSelectedItem();
        String seatNumber = (String) seatNumberComboBox.getSelectedItem();

        if (isSeatDuplicated(showTime.getShowTimeId(), seatNumber, index)) {
            JOptionPane.showMessageDialog(this, "Ghe nay da duoc dat trong suat chieu nay");
            return;
        }

        updatePrice();

        String newTicket =
                ticketIdField.getText() + ";" +
                        customerNameField.getText().trim() + ";" +
                        phoneField.getText().trim() + ";" +
                        emailField.getText().trim() + ";" +
                        movie.getMovieId() + ";" +
                        movie.getMovieName() + ";" +
                        showTime.getShowTimeId() + ";" +
                        showTime.getShowTime() + "-" + showTime.getEndTime() + ";" +
                        seatNumber + ";" +
                        priceField.getText() + ";" +
                        statusField.getText();

        tickets.set(index, newTicket);
        ticketService.saveAllTicketLines(tickets);
        loadTickets();

        JOptionPane.showMessageDialog(this, "Cap nhat ve thanh cong");
    }

    private void updatePrice() {
        ShowTime showTime = (ShowTime) showTimeComboBox.getSelectedItem();
        SeatType seatType = (SeatType) seatTypeComboBox.getSelectedItem();

        if (showTime == null || seatType == null) {
            priceField.setText("0");
            return;
        }

        double price = showTime.getBasePrice();

        if (seatType == SeatType.VIP) {
            price += 30000;
        }

        if (seatType == SeatType.COUPLE) {
            price += 50000;
        }

        priceField.setText(String.valueOf(price));
    }

    private boolean isCurrentTicketSeat(String showTimeId, String seat, int currentIndex) {
        if (currentIndex < 0 || currentIndex >= tickets.size()) {
            return false;
        }

        String[] p = tickets.get(currentIndex).split(";");

        return p.length == 11 &&
                p[6].equalsIgnoreCase(showTimeId) &&
                p[8].equalsIgnoreCase(seat);
    }

    private boolean isSeatDuplicated(String showTimeId, String seat, int currentIndex) {
        for (int i = 0; i < tickets.size(); i++) {
            if (i == currentIndex) {
                continue;
            }

            String[] p = tickets.get(i).split(";");

            if (p.length == 11 &&
                    p[6].equalsIgnoreCase(showTimeId) &&
                    p[8].equalsIgnoreCase(seat)) {
                return true;
            }
        }

        return false;
    }

    private void selectMovieById(String movieId) {
        for (int i = 0; i < movieComboBox.getItemCount(); i++) {
            Movie movie = movieComboBox.getItemAt(i);

            if (movie.getMovieId().equalsIgnoreCase(movieId)) {
                movieComboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selectShowTimeById(String showTimeId) {
        for (int i = 0; i < showTimeComboBox.getItemCount(); i++) {
            ShowTime showTime = showTimeComboBox.getItemAt(i);

            if (showTime.getShowTimeId().equalsIgnoreCase(showTimeId)) {
                showTimeComboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selectSeatTypeBySeatNumber(String seatNumber) {
        if (seatNumber.startsWith("N")) {
            seatTypeComboBox.setSelectedItem(SeatType.NORMAL);
        } else if (seatNumber.startsWith("V")) {
            seatTypeComboBox.setSelectedItem(SeatType.VIP);
        } else if (seatNumber.startsWith("C")) {
            seatTypeComboBox.setSelectedItem(SeatType.COUPLE);
        }
    }

    private boolean validateTicketForm() {
        return ValidationUtils.requireTextField(this, customerNameField, "ten khach hang") &&
                ValidationUtils.requireTextField(this, phoneField, "so dien thoai") &&
                ValidationUtils.requireTextField(this, emailField, "email") &&
                ValidationUtils.requireComboBox(this, movieComboBox, "phim") &&
                ValidationUtils.requireComboBox(this, showTimeComboBox, "suat chieu") &&
                ValidationUtils.requireComboBox(this, seatTypeComboBox, "loai ghe") &&
                ValidationUtils.requireComboBox(this, seatNumberComboBox, "ma ghe");
    }

    private void clearForm() {
        ticketIdField.setText("");
        customerNameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        priceField.setText("");
        statusField.setText("");
    }
}