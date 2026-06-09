package com.nhom6.admin;

import com.nhom6.model.Movie;
import com.nhom6.model.MovieStatus;
import com.nhom6.service.MovieService;
import com.nhom6.utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;

public class MovieManagementFrame extends JFrame {
    private MovieService movieService = new MovieService();

    private JTextField movieIdField = new JTextField();
    private JTextField movieNameField = new JTextField();
    private JTextField genreField = new JTextField();
    private JTextField durationField = new JTextField();
    private JTextField ageLimitField = new JTextField();
    private JComboBox<MovieStatus> statusBox = new JComboBox<>(MovieStatus.values());

    public MovieManagementFrame() {
        setTitle("Quan Ly Phim");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        JButton addButton = new JButton("Them phim");

        add(new JLabel("Ma phim"));
        add(movieIdField);
        add(new JLabel("Ten phim"));
        add(movieNameField);
        add(new JLabel("The loai"));
        add(genreField);
        add(new JLabel("Thoi luong"));
        add(durationField);
        add(new JLabel("Do tuoi"));
        add(ageLimitField);
        add(new JLabel("Trang thai"));
        add(statusBox);
        add(new JLabel());
        add(addButton);

        addButton.addActionListener(e -> addMovie());
    }

    private void addMovie() {
        if (!validateForm()) {
            return;
        }

        if (movieService.isDuplicateMovieId(movieIdField.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Ma phim da ton tai");
            return;
        }

        Movie movie = new Movie(
                movieIdField.getText().trim(),
                movieNameField.getText().trim(),
                genreField.getText().trim(),
                Integer.parseInt(durationField.getText().trim()),
                Integer.parseInt(ageLimitField.getText().trim()),
                (MovieStatus) statusBox.getSelectedItem()
        );

        movieService.addMovie(movie);
        JOptionPane.showMessageDialog(this, "Them phim thanh cong");
    }

    private boolean validateForm() {
        return ValidationUtils.requireTextField(this, movieIdField, "ma phim") &&
                ValidationUtils.requireTextField(this, movieNameField, "ten phim") &&
                ValidationUtils.requireTextField(this, genreField, "the loai") &&
                ValidationUtils.requireTextField(this, durationField, "thoi luong") &&
                ValidationUtils.requireTextField(this, ageLimitField, "do tuoi");
    }
}