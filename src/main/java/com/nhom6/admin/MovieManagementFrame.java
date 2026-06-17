package com.nhom6.admin;

import com.nhom6.model.Movie;
import com.nhom6.service.MovieService;
import com.nhom6.utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.nhom6.model.MovieStatus;

public class MovieManagementFrame extends JFrame {
    private MovieService movieService = new MovieService();
    private List<Movie> movies;

    private JTextField movieIdField = new JTextField();
    private JTextField movieNameField = new JTextField();
    private JTextField genreField = new JTextField();
    private JTextField durationField = new JTextField();
    private JTextField ageLimitField = new JTextField();
    private JComboBox<MovieStatus> statusBox = new JComboBox<>(MovieStatus.values());

    private DefaultListModel<Movie> movieListModel = new DefaultListModel<>();
    private JList<Movie> movieList = new JList<>(movieListModel);

    public MovieManagementFrame() {
        setTitle("Quan Ly Phim");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        formPanel.add(new JLabel("Ma phim"));
        formPanel.add(movieIdField);
        formPanel.add(new JLabel("Ten phim"));
        formPanel.add(movieNameField);
        formPanel.add(new JLabel("The loai"));
        formPanel.add(genreField);
        formPanel.add(new JLabel("Thoi luong"));
        formPanel.add(durationField);
        formPanel.add(new JLabel("Do tuoi"));
        formPanel.add(ageLimitField);
        formPanel.add(new JLabel("Trang thai"));
        formPanel.add(statusBox);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton addButton = new JButton("Them phim");
        JButton updateButton = new JButton("Sua phim");
        JButton deleteButton = new JButton("Xoa phim");
        JButton displayButton = new JButton("Hien thi phim");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayButton);

        movieList.setPreferredSize(new Dimension(260, 400));

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(movieList),
                formPanel
        );

        splitPane.setDividerLocation(260);

        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadMoviesToList();

        movieList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Movie selectedMovie = movieList.getSelectedValue();

                if (selectedMovie != null) {
                    movieIdField.setText(selectedMovie.getMovieId());
                    movieNameField.setText(selectedMovie.getMovieName());
                    genreField.setText(selectedMovie.getGenre());
                    durationField.setText(String.valueOf(selectedMovie.getDuration()));
                    ageLimitField.setText(String.valueOf(selectedMovie.getAgeLimit()));
                    statusBox.setSelectedItem(selectedMovie.getStatus());
                }
            }
        });

        addButton.addActionListener(e -> addMovie());
        updateButton.addActionListener(e -> updateMovie());
        deleteButton.addActionListener(e -> deleteMovie());
        displayButton.addActionListener(e -> loadMoviesToList());
    }

    private void loadMoviesToList() {
        movies = movieService.getAllMovies();
        movieListModel.clear();

        for (Movie movie : movies) {
            movieListModel.addElement(movie);
        }

        clearForm();
    }

    private void addMovie() {
        if (!validateMovieForm()) {
            return;
        }

        try {
            String movieId = movieIdField.getText().trim();

            if (movieService.isDuplicateMovieId(movieId)) {
                JOptionPane.showMessageDialog(this, "Ma phim da ton tai, khong the them");
                return;
            }

            Movie movie = new Movie(
                    movieId,
                    movieNameField.getText().trim(),
                    genreField.getText().trim(),
                    Integer.parseInt(durationField.getText().trim()),
                    Integer.parseInt(ageLimitField.getText().trim()),
                    (MovieStatus) statusBox.getSelectedItem()
            );

            movieService.addMovie(movie);
            loadMoviesToList();

            JOptionPane.showMessageDialog(this, "Them phim thanh cong");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Thoi luong va do tuoi phai la so");
        }
    }

    private void updateMovie() {
        Movie selectedMovie = movieList.getSelectedValue();

        if (selectedMovie == null) {
            JOptionPane.showMessageDialog(this, "Hay chon phim can sua");
            return;
        }

        if (!validateMovieForm()) {
            return;
        }

        try {
            String movieId = movieIdField.getText().trim();

            if (movieService.isDuplicateMovieIdForUpdate(movieId, selectedMovie)) {
                JOptionPane.showMessageDialog(this, "Ma phim da ton tai, khong the sua");
                return;
            }

            Movie newMovie = new Movie(
                    movieId,
                    movieNameField.getText().trim(),
                    genreField.getText().trim(),
                    Integer.parseInt(durationField.getText().trim()),
                    Integer.parseInt(ageLimitField.getText().trim()),
                    (MovieStatus) statusBox.getSelectedItem()
            );

            movieService.updateMovie(selectedMovie, newMovie);
            loadMoviesToList();

            JOptionPane.showMessageDialog(this, "Sua phim thanh cong");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Thoi luong va do tuoi phai la so");
        }
    }

    private void deleteMovie() {
        Movie selectedMovie = movieList.getSelectedValue();

        if (selectedMovie == null) {
            JOptionPane.showMessageDialog(this, "Hay chon phim can xoa");
            return;
        }

        movieService.deleteMovie(selectedMovie);
        loadMoviesToList();

        JOptionPane.showMessageDialog(this, "Xoa phim thanh cong");
    }

    private boolean validateMovieForm() {
        return ValidationUtils.requireTextField(this, movieIdField, "ma phim") &&
                ValidationUtils.requireTextField(this, movieNameField, "ten phim") &&
                ValidationUtils.requireTextField(this, genreField, "the loai") &&
                ValidationUtils.requireTextField(this, durationField, "thoi luong") &&
                ValidationUtils.requireTextField(this, ageLimitField, "do tuoi") &&
                ValidationUtils.requireComboBox(this, statusBox, "trang thai");
    }

    private void clearForm() {
        movieIdField.setText("");
        movieNameField.setText("");
        genreField.setText("");
        durationField.setText("");
        ageLimitField.setText("");
        statusBox.setSelectedIndex(0);
    }
}