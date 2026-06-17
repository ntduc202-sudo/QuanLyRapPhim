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
import java.util.List;

public class ShowTimeManagementFrame extends JFrame {
    private MovieService movieService = new MovieService();
    private ShowTimeService showTimeService = new ShowTimeService();
    private CinemaRoomService cinemaRoomService = new CinemaRoomService();

    private List<ShowTime> showTimes;

    private JTextField showTimeIdField = new JTextField();
    private JComboBox<Movie> movieComboBox = new JComboBox<>();
    private JComboBox<CinemaRoom> roomComboBox = new JComboBox<>();
    private JTextField showDateField = new JTextField();
    private JTextField startTimeField = new JTextField();
    private JTextField endTimeField = new JTextField();
    private JTextField basePriceField = new JTextField();

    private DefaultListModel<ShowTime> showTimeListModel = new DefaultListModel<>();
    private JList<ShowTime> showTimeList = new JList<>(showTimeListModel);

    public ShowTimeManagementFrame() {
        setTitle("Quan Ly Suat Chieu");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        endTimeField.setEditable(false);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        formPanel.add(new JLabel("Ma suat chieu"));
        formPanel.add(showTimeIdField);
        formPanel.add(new JLabel("Phim"));
        formPanel.add(movieComboBox);
        formPanel.add(new JLabel("Ngay chieu"));
        formPanel.add(showDateField);
        formPanel.add(new JLabel("Gio bat dau"));
        formPanel.add(startTimeField);
        formPanel.add(new JLabel("Gio ket thuc"));
        formPanel.add(endTimeField);
        formPanel.add(new JLabel("Phong"));
        formPanel.add(roomComboBox);
        formPanel.add(new JLabel("Gia ve co ban"));
        formPanel.add(basePriceField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton addButton = new JButton("Them suat chieu");
        JButton updateButton = new JButton("Sua suat chieu");
        JButton deleteButton = new JButton("Xoa suat chieu");
        JButton displayButton = new JButton("Hien thi");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayButton);

        showTimeList.setPreferredSize(new Dimension(260, 430));

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(showTimeList),
                formPanel
        );

        splitPane.setDividerLocation(260);

        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadMoviesToComboBox();
        loadRoomsToComboBox();
        loadShowTimesToList();

        movieComboBox.addActionListener(e -> {
            updateEndTime();
            loadShowTimesToList();
        });

        startTimeField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateEndTime();
            }
        });

        showTimeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                ShowTime selected = showTimeList.getSelectedValue();

                if (selected != null) {
                    showTimeIdField.setText(selected.getShowTimeId());
                    selectMovieById(selected.getMovieId());
                    showDateField.setText(selected.getShowDate());
                    startTimeField.setText(selected.getShowTime());
                    endTimeField.setText(selected.getEndTime());
                    selectRoomById(selected.getRoom());
                    basePriceField.setText(String.valueOf(selected.getBasePrice()));
                }
            }
        });

        addButton.addActionListener(e -> addShowTime());
        updateButton.addActionListener(e -> updateShowTime());
        deleteButton.addActionListener(e -> deleteShowTime());
        displayButton.addActionListener(e -> loadShowTimesToList());
    }

    private void loadMoviesToComboBox() {
        movieComboBox.removeAllItems();

        for (Movie movie : movieService.getAllMovies()) {
            movieComboBox.addItem(movie);
        }
    }

    private void loadRoomsToComboBox() {
        roomComboBox.removeAllItems();

        for (CinemaRoom room : cinemaRoomService.getAllRooms()) {
            roomComboBox.addItem(room);
        }
    }

    private void loadShowTimesToList() {
        Movie selectedMovie = (Movie) movieComboBox.getSelectedItem();

        showTimeListModel.clear();

        if (selectedMovie == null) {
            showTimes = showTimeService.getAllShowTimes();
        } else {
            showTimes = showTimeService.getShowTimesByMovieId(selectedMovie.getMovieId());
        }

        for (ShowTime showTime : showTimes) {
            showTimeListModel.addElement(showTime);
        }
    }

    private void addShowTime() {
        if (!validateShowTimeForm()) {
            return;
        }

        try {
            Movie selectedMovie = (Movie) movieComboBox.getSelectedItem();
            CinemaRoom selectedRoom = (CinemaRoom) roomComboBox.getSelectedItem();

            String showTimeId = showTimeIdField.getText().trim();
            String showDate = showDateField.getText().trim();
            String startTime = startTimeField.getText().trim();
            String endTime = calculateEndTime(startTime, selectedMovie.getDuration());

            endTimeField.setText(endTime);

            if (showTimeService.isDuplicateShowTimeId(showTimeId)) {
                JOptionPane.showMessageDialog(this, "Ma suat chieu da ton tai");
                return;
            }

            if (showTimeService.isRoomBusy(selectedRoom.getRoomId(), showDate, startTime, endTime)) {
                JOptionPane.showMessageDialog(this, "Phong nay da co suat chieu trong khoang thoi gian nay");
                return;
            }

            ShowTime showTime = new ShowTime(
                    showTimeId,
                    selectedMovie.getMovieId(),
                    showDate,
                    startTime,
                    endTime,
                    selectedRoom.getRoomId(),
                    Double.parseDouble(basePriceField.getText().trim())
            );

            if (showTime.getBasePrice() <= 0) {
                JOptionPane.showMessageDialog(this, "Gia ve phai lon hon 0");
                return;
            }

            showTimeService.addShowTime(showTime);
            loadShowTimesToList();
            clearForm();

            JOptionPane.showMessageDialog(this, "Them suat chieu thanh cong");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Gia ve phai la so, gio bat dau dung dinh dang HH:mm");
        }
    }

    private void updateShowTime() {
        ShowTime selected = showTimeList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Hay chon suat chieu can sua");
            return;
        }

        if (!validateShowTimeForm()) {
            return;
        }

        try {
            Movie selectedMovie = (Movie) movieComboBox.getSelectedItem();
            CinemaRoom selectedRoom = (CinemaRoom) roomComboBox.getSelectedItem();

            String showTimeId = showTimeIdField.getText().trim();
            String showDate = showDateField.getText().trim();
            String startTime = startTimeField.getText().trim();
            String endTime = calculateEndTime(startTime, selectedMovie.getDuration());

            endTimeField.setText(endTime);

            if (showTimeService.isDuplicateShowTimeIdForUpdate(showTimeId, selected)) {
                JOptionPane.showMessageDialog(this, "Ma suat chieu da ton tai");
                return;
            }

            if (showTimeService.isRoomBusy(selectedRoom.getRoomId(), showDate, startTime, endTime, selected)) {
                JOptionPane.showMessageDialog(this, "Phong nay da co suat chieu trong khoang thoi gian nay");
                return;
            }

            ShowTime newShowTime = new ShowTime(
                    showTimeId,
                    selectedMovie.getMovieId(),
                    showDate,
                    startTime,
                    endTime,
                    selectedRoom.getRoomId(),
                    Double.parseDouble(basePriceField.getText().trim())
            );

            if (newShowTime.getBasePrice() <= 0) {
                JOptionPane.showMessageDialog(this, "Gia ve phai lon hon 0");
                return;
            }

            showTimeService.updateShowTime(selected, newShowTime);
            loadShowTimesToList();

            JOptionPane.showMessageDialog(this, "Sua suat chieu thanh cong");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Gia ve phai la so, gio bat dau dung dinh dang HH:mm");
        }
    }

    private void deleteShowTime() {
        ShowTime selected = showTimeList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Hay chon suat chieu can xoa");
            return;
        }

        showTimeService.deleteShowTime(selected);
        loadShowTimesToList();
        clearForm();

        JOptionPane.showMessageDialog(this, "Xoa suat chieu thanh cong");
    }

    private boolean validateShowTimeForm() {
        return ValidationUtils.requireTextField(this, showTimeIdField, "ma suat chieu") &&
                ValidationUtils.requireComboBox(this, movieComboBox, "phim") &&
                ValidationUtils.requireTextField(this, showDateField, "ngay chieu") &&
                ValidationUtils.requireTextField(this, startTimeField, "gio bat dau") &&
                ValidationUtils.requireComboBox(this, roomComboBox, "phong") &&
                ValidationUtils.requireTextField(this, basePriceField, "gia ve co ban");
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

    private void selectRoomById(String roomId) {
        for (int i = 0; i < roomComboBox.getItemCount(); i++) {
            CinemaRoom room = roomComboBox.getItemAt(i);

            if (room.getRoomId().equalsIgnoreCase(roomId)) {
                roomComboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    private void updateEndTime() {
        Movie movie = (Movie) movieComboBox.getSelectedItem();

        if (movie == null) {
            return;
        }

        String startTime = startTimeField.getText().trim();

        if (startTime.matches("\\d{2}:\\d{2}")) {
            endTimeField.setText(calculateEndTime(startTime, movie.getDuration()));
        }
    }

    private String calculateEndTime(String startTime, int duration) {
        String[] parts = startTime.split(":");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        int totalMinutes = hour * 60 + minute + duration;

        int endHour = (totalMinutes / 60) % 24;
        int endMinute = totalMinutes % 60;

        return String.format("%02d:%02d", endHour, endMinute);
    }

    private void clearForm() {
        showTimeIdField.setText("");
        showDateField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
        basePriceField.setText("");

        if (roomComboBox.getItemCount() > 0) {
            roomComboBox.setSelectedIndex(0);
        }
    }
}