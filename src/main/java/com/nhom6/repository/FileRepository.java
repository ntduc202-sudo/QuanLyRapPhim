package com.nhom6.repository;

import com.nhom6.model.Movie;
import com.nhom6.model.Ticket;
import com.nhom6.model.ShowTime;
import com.nhom6.model.Customer;
import com.nhom6.model.CustomerType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.nhom6.model.MovieStatus;

public class FileRepository {
    private static final String MOVIE_FILE = "movies.txt";
    private static final String TICKET_FILE = "tickets.txt";
    private static final String SHOWTIME_FILE = "showtimes.txt";
    private static final String CUSTOMER_FILE = "customers.txt";

    public void saveMovie(Movie movie) {
        try (FileWriter writer = new FileWriter(MOVIE_FILE, true)) {
            writer.write(movie.getMovieId() + ";" + movie.getMovieName() + ";" + movie.getGenre() + ";" + movie.getDuration() + ";" + movie.getAgeLimit() + ";" + movie.getStatus());
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Loi khi luu phim vao file");
        }
    }

    public List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length == 6) {
                    Movie movie = new Movie(
                            parts[0],
                            parts[1],
                            parts[2],
                            Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]),
                            MovieStatus.valueOf(parts[5])
                    );

                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            return movies;
        }

        return movies;
    }

    public void saveAllMovies(List<Movie> movies) {
        try (FileWriter writer = new FileWriter(MOVIE_FILE, false)) {
            for (Movie movie : movies) {
                writer.write(movie.getMovieId() + ";" + movie.getMovieName() + ";" + movie.getGenre() + ";" + movie.getDuration() + ";" + movie.getAgeLimit() + ";" + movie.getStatus());
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Loi khi ghi lai danh sach phim");
        }
    }

    public void saveTicket(Ticket ticket) {
        try (FileWriter writer = new FileWriter(TICKET_FILE, true)) {
            writer.write(ticket.toFileString());
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Loi khi luu ve vao file");
        }
    }

    public String loadTicketsAsText() {
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(TICKET_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Chua co ve nao duoc dat";
        }

        if (result.isEmpty()) {
            return "Chua co ve nao duoc dat";
        }

        return result.toString();
    }
    public List<ShowTime> loadShowTimes() {
        List<ShowTime> showTimes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(SHOWTIME_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length == 7) {
                    ShowTime showTime = new ShowTime(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4],
                            parts[5],
                            Double.parseDouble(parts[6])
                    );

                    showTimes.add(showTime);
                }
            }
        } catch (IOException e) {
            return showTimes;
        }

        return showTimes;
    }

    public void saveAllShowTimes(List<ShowTime> showTimes) {
        try (FileWriter writer = new FileWriter(SHOWTIME_FILE, false)) {
            for (ShowTime showTime : showTimes) {
                writer.write(
                        showTime.getShowTimeId() + ";" +
                                showTime.getMovieId() + ";" +
                                showTime.getShowDate() + ";" +
                                showTime.getShowTime() + ";" +
                                showTime.getEndTime() + ";" +
                                showTime.getRoom() + ";" +
                                showTime.getBasePrice()
                );
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Loi khi ghi lai danh sach suat chieu");
        }
    }



    public List<ShowTime> loadShowTimesByMovieId(String movieId) {
        List<ShowTime> result = new ArrayList<>();

        for (ShowTime showTime : loadShowTimes()) {
            if (showTime.getMovieId().equalsIgnoreCase(movieId)) {
                result.add(showTime);
            }
        }
        return result;
    }
    public boolean isSeatBooked(String showTimeId, String seatNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(TICKET_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length == 11) {
                    String fileShowTimeId = parts[6];
                    String fileSeatNumber = parts[8];

                    if (fileShowTimeId.equalsIgnoreCase(showTimeId) &&
                            fileSeatNumber.equalsIgnoreCase(seatNumber)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            return false;
        }

        return false;
    }
    public List<String> loadTicketLines() {
        List<String> tickets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(TICKET_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                tickets.add(line);
            }
        } catch (IOException e) {
            return tickets;
        }

        return tickets;
    }

    public void saveAllTicketLines(List<String> tickets) {
        try (FileWriter writer = new FileWriter(TICKET_FILE, false)) {
            for (String ticket : tickets) {
                writer.write(ticket);
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Loi khi ghi lai danh sach ve");
        }
    }
    public void saveCustomer(Customer customer) {
        try (FileWriter writer = new FileWriter(CUSTOMER_FILE, true)) {
            writer.write(
                    customer.getUserId() + ";" +
                            customer.getFullName() + ";" +
                            customer.getPhone() + ";" +
                            customer.getEmail() + ";" +
                            customer.getCustomerType()
            );
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Loi khi luu khach hang");
        }
    }

    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(";");

                if (p.length == 5) {
                    Customer customer = new Customer(
                            p[0],
                            p[1],
                            p[2],
                            p[3],
                            CustomerType.valueOf(p[4])
                    );

                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            return customers;
        }

        return customers;
    }

    public boolean isDuplicateCustomerId(String customerId) {
        for (Customer customer : loadCustomers()) {
            if (customer.getUserId().equalsIgnoreCase(customerId)) {
                return true;
            }
        }
        return false;
    }
}