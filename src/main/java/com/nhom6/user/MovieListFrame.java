package com.nhom6.user;

import com.nhom6.model.Movie;
import com.nhom6.service.MovieService;

import javax.swing.*;
import java.awt.*;

public class MovieListFrame extends JFrame {
    private MovieService movieService = new MovieService();

    public MovieListFrame() {
        setTitle("Danh Sach Phim");
        setSize(500, 350);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        for (Movie movie : movieService.getAllMovies()) {
            area.append(movie.toString() + "\n");
        }

        if (area.getText().isEmpty()) {
            area.setText("Chua co phim nao");
        }

        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}