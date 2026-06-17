package com.nhom6.user;

import com.nhom6.model.*;
import com.nhom6.service.*;
import com.nhom6.utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;

public class BookingFrame extends JFrame {
    private CustomerService customerService = new CustomerService();
    private MovieService movieService = new MovieService();
    private ShowTimeService showTimeService = new ShowTimeService();
    private BookingService bookingService = new BookingService();
    private SeatService seatService = new SeatService();
    private TicketService ticketService = new TicketService();

    private JComboBox<Customer> customerBox = new JComboBox<>();
    private JComboBox<Movie> movieBox = new JComboBox<>();
    private JComboBox<ShowTime> showTimeBox = new JComboBox<>();
    private JComboBox<SeatType> seatTypeBox = new JComboBox<>(SeatType.values());
    private JComboBox<String> seatNumberBox = new JComboBox<>();

    private JLabel priceLabel = new JLabel("So tien: 0 VND");

    public BookingFrame() {
        setTitle("Dat Ve");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        JButton bookButton = new JButton("Dat ve");

        loadCustomers();
        loadMovies();

        add(new JLabel("Khach hang"));
        add(customerBox);
        add(new JLabel("Phim"));
        add(movieBox);
        add(new JLabel("Suat chieu"));
        add(showTimeBox);
        add(new JLabel("Loai ghe"));
        add(seatTypeBox);
        add(new JLabel("Ma ghe"));
        add(seatNumberBox);
        add(new JLabel("Gia ve"));
        add(priceLabel);
        add(new JLabel());
        add(bookButton);

        movieBox.addActionListener(e -> {
            loadShowTimes();
            loadSeats();
            updatePrice();
        });

        showTimeBox.addActionListener(e -> {
            loadSeats();
            updatePrice();
        });

        seatTypeBox.addActionListener(e -> {
            loadSeats();
            updatePrice();
        });

        customerBox.addActionListener(e -> updatePrice());
        seatNumberBox.addActionListener(e -> updatePrice());

        bookButton.addActionListener(e -> bookTicket());

        loadShowTimes();
        loadSeats();
        updatePrice();
    }

    private void loadCustomers() {
        for (Customer customer : customerService.getAllCustomers()) {
            customerBox.addItem(customer);
        }
    }

    private void loadMovies() {
        for (Movie movie : movieService.getAllMovies()) {
            movieBox.addItem(movie);
        }
    }

    private void loadShowTimes() {
        showTimeBox.removeAllItems();

        Movie movie = (Movie) movieBox.getSelectedItem();

        if (movie == null) {
            return;
        }

        for (ShowTime showTime : showTimeService.getAllShowTimes()) {
            if (showTime.getMovieId().equalsIgnoreCase(movie.getMovieId())) {
                showTimeBox.addItem(showTime);
            }
        }
    }

    private void loadSeats() {
        seatNumberBox.removeAllItems();

        ShowTime showTime = (ShowTime) showTimeBox.getSelectedItem();
        SeatType seatType = (SeatType) seatTypeBox.getSelectedItem();

        if (showTime == null || seatType == null) {
            return;
        }

        if (!seatService.hasAvailableSeat(showTime.getShowTimeId())) {
            JOptionPane.showMessageDialog(this, "Suat chieu nay da het ghe");
            return;
        }

        for (String seat : seatService.getSeatNumbersByType(seatType, showTime.getShowTimeId())) {
            seatNumberBox.addItem(seat);
        }
    }

    private void bookTicket() {
        if (!validateForm()) {
            return;
        }

        Customer customer = (Customer) customerBox.getSelectedItem();
        Movie movie = (Movie) movieBox.getSelectedItem();
        ShowTime showTime = (ShowTime) showTimeBox.getSelectedItem();
        SeatType seatType = (SeatType) seatTypeBox.getSelectedItem();
        String seatNumber = (String) seatNumberBox.getSelectedItem();

        if (movie.getStatus() != MovieStatus.DANG_CHIEU) {
            JOptionPane.showMessageDialog(this, "Chi duoc dat ve phim dang chieu");
            return;
        }

        if (seatNumber.contains("DA DAT")) {
            JOptionPane.showMessageDialog(this, "Ghe nay da duoc dat");
            return;
        }

        Seat seat = new Seat(seatNumber, seatNumber, seatType, SeatStatus.AVAILABLE);

        Ticket ticket = bookingService.bookTicket(customer, movie, showTime, seat);

        if (ticket != null) {
            ticketService.saveTicket(ticket);
            JOptionPane.showMessageDialog(this, "Dat ve thanh cong");
            loadSeats();
        } else {
            JOptionPane.showMessageDialog(this, "Dat ve that bai");
        }
    }

    private boolean validateForm() {
        return ValidationUtils.requireComboBox(this, customerBox, "khach hang") &&
                ValidationUtils.requireComboBox(this, movieBox, "phim") &&
                ValidationUtils.requireComboBox(this, showTimeBox, "suat chieu") &&
                ValidationUtils.requireComboBox(this, seatTypeBox, "loai ghe") &&
                ValidationUtils.requireComboBox(this, seatNumberBox, "ma ghe");
    }

    private void updatePrice() {
        Customer customer = (Customer) customerBox.getSelectedItem();
        ShowTime showTime = (ShowTime) showTimeBox.getSelectedItem();
        SeatType seatType = (SeatType) seatTypeBox.getSelectedItem();

        if (customer == null || showTime == null || seatType == null) {
            priceLabel.setText("So tien: 0 VND");
            return;
        }

        double price = showTime.getBasePrice();

        if (seatType == SeatType.VIP) {
            price += 30000;
        }

        if (seatType == SeatType.COUPLE) {
            price += 50000;
        }

        if (customer.getCustomerType() == CustomerType.STUDENT) {
            price *= 0.9;
        }

        if (customer.getCustomerType() == CustomerType.VIP) {
            price *= 0.8;
        }

        priceLabel.setText("So tien: " + String.format("%,.0f", price) + " VND");
    }
}