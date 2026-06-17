package com.nhom6.user;

import com.nhom6.model.Movie;
import com.nhom6.service.MovieService;

import javax.swing.*;
import java.awt.*;

public class SearchMovieFrame extends JFrame {
    private MovieService movieService = new MovieService();
    private JTextField searchField = new JTextField();
    private JTextArea resultArea = new JTextArea();

    public SearchMovieFrame() {
        setTitle("Tim Phim");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        JButton searchButton = new JButton("Tim");

        topPanel.add(new JLabel("Nhap ten phim"), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        resultArea.setEditable(false);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener(e -> searchMovie());
    }

    private void searchMovie() {
        String keyword = searchField.getText().trim().toLowerCase();
        resultArea.setText("");

        for (Movie movie : movieService.getAllMovies()) {
            if (movie.getMovieName().toLowerCase().contains(keyword)) {
                resultArea.append(movie.toString() + "\n");
            }
        }

        if (resultArea.getText().isEmpty()) {
            resultArea.setText("Khong tim thay phim");
        }
    }
}
