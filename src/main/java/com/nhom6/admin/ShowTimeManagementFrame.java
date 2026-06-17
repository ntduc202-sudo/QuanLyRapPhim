package com.nhom6.admin;

import com.nhom6.model.CinemaRoom;
import com.nhom6.model.Movie;
import com.nhom6.model.ShowTime;
import com.nhom6.service.CinemaRoomService;
import com.nhom6.service.MovieService;
import com.nhom6.service.ShowTimeService;
import com.nhom6.utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;

public class ShowTimeManagementFrame extends JFrame {
    private MovieService movieService = new MovieService();
    private ShowTimeService showTimeService = new ShowTimeService();
    private CinemaRoomService cinemaRoomService = new CinemaRoomService();

    private JTextField showTimeIdField = new JTextField();
    private JComboBox<Movie> movieBox = new JComboBox<>();
    private JTextField showDateField = new JTextField();
    private JTextField startTimeField = new JTextField();
    private JTextField endTimeField = new JTextField();
    private JComboBox<CinemaRoom> roomBox = new JComboBox<>();
    private JTextField basePriceField = new JTextField();

    public ShowTimeManagementFrame() {
        setTitle("Quan Ly Suat Chieu");
        setSize(550, 420);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 10, 10));

        endTimeField.setEditable(false);

        loadMovies();
        loadRooms();

        JButton addButton = new JButton("Them suat chieu");

        add(new JLabel("Ma suat chieu"));
        add(showTimeIdField);
        add(new JLabel("Phim"));
        add(movieBox);
        add(new JLabel("Ngay chieu"));
        add(showDateField);
        add(new JLabel("Gio bat dau"));
        add(startTimeField);
        add(new JLabel("Gio ket thuc"));
        add(endTimeField);
        add(new JLabel("Phong"));
        add(roomBox);
        add(new JLabel("Gia ve co ban"));
        add(basePriceField);
        add(new JLabel());
        add(addButton);

        startTimeField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateEndTime();
            }
        });

        movieBox.addActionListener(e -> updateEndTime());
        addButton.addActionListener(e -> addShowTime());
    }

    private void loadMovies() {
        for (Movie movie : movieService.getAllMovies()) {
            movieBox.addItem(movie);
        }
    }

    private void loadRooms() {
        for (CinemaRoom room : cinemaRoomService.getAllRooms()) {
            roomBox.addItem(room);
        }
    }

    private void addShowTime() {
        if (!validateForm()) {
            return;
        }

        try {
            Movie movie = (Movie) movieBox.getSelectedItem();
            CinemaRoom room = (CinemaRoom) roomBox.getSelectedItem();

            String showTimeId = showTimeIdField.getText().trim();
            String showDate = showDateField.getText().trim();
            String startTime = startTimeField.getText().trim();
            String endTime = calculateEndTime(startTime, movie.getDuration());

            if (showTimeService.isDuplicateShowTimeId(showTimeId)) {
                JOptionPane.showMessageDialog(this, "Ma suat chieu da ton tai");
                return;
            }

            if (showTimeService.isRoomBusy(room.getRoomId(), showDate, startTime, endTime)) {
                JOptionPane.showMessageDialog(this, "Phong da co suat chieu trong khoang thoi gian nay");
                return;
            }

            ShowTime showTime = new ShowTime(
                    showTimeId,
                    movie.getMovieId(),
                    showDate,
                    startTime,
                    endTime,
                    room.getRoomId(),
                    Double.parseDouble(basePriceField.getText().trim())
            );

            if (showTime.getBasePrice() <= 0) {
                JOptionPane.showMessageDialog(this, "Gia ve phai lon hon 0");
                return;
            }

            showTimeService.addShowTime(showTime);
            JOptionPane.showMessageDialog(this, "Them suat chieu thanh cong");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Gia ve phai la so");
        }
    }

    private boolean validateForm() {
        return ValidationUtils.requireTextField(this, showTimeIdField, "ma suat chieu") &&
                ValidationUtils.requireComboBox(this, movieBox, "phim") &&
                ValidationUtils.requireTextField(this, showDateField, "ngay chieu") &&
                ValidationUtils.requireTextField(this, startTimeField, "gio bat dau") &&
                ValidationUtils.requireComboBox(this, roomBox, "phong") &&
                ValidationUtils.requireTextField(this, basePriceField, "gia ve co ban");
    }

    private void updateEndTime() {
        Movie movie = (Movie) movieBox.getSelectedItem();

        if (movie == null || !startTimeField.getText().trim().matches("\\d{2}:\\d{2}")) {
            return;
        }

        endTimeField.setText(calculateEndTime(startTimeField.getText().trim(), movie.getDuration()));
    }

    private String calculateEndTime(String startTime, int duration) {
        String[] p = startTime.split(":");
        int total = Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]) + duration;
        return String.format("%02d:%02d", (total / 60) % 24, total % 60);
    }
}