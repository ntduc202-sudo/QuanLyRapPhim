package com.nhom6.repository;

import com.nhom6.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {
    private static final String MOVIE_FILE = "movies.txt";
    private static final String CUSTOMER_FILE = "customers.txt";
    private static final String SHOWTIME_FILE = "showtimes.txt";
    private static final String TICKET_FILE = "tickets.txt";

    public List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(";");

                if (p.length == 6) {
                    movies.add(new Movie(
                            p[0],
                            p[1],
                            p[2],
                            Integer.parseInt(p[3]),
                            Integer.parseInt(p[4]),
                            MovieStatus.valueOf(p[5])
                    ));
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
                writer.write(movie.getMovieId() + ";" +
                        movie.getMovieName() + ";" +
                        movie.getGenre() + ";" +
                        movie.getDuration() + ";" +
                        movie.getAgeLimit() + ";" +
                        movie.getStatus());
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Loi khi luu danh sach phim");
        }
    }

    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(";");

                if (p.length == 5) {
                    customers.add(new Customer(
                            p[0],
                            p[1],
                            p[2],
                            p[3],
                            CustomerType.valueOf(p[4])
                    ));
                }
            }
        } catch (IOException e) {
            return customers;
        }

        return customers;
    }

    public void saveCustomer(Customer customer) {
        try (FileWriter writer = new FileWriter(CUSTOMER_FILE, true)) {
            writer.write(customer.getUserId() + ";" +
                    customer.getFullName() + ";" +
                    customer.getPhone() + ";" +
                    customer.getEmail() + ";" +
                    customer.getCustomerType());
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Loi khi luu khach hang");
        }
    }

    public List<ShowTime> loadShowTimes() {
        List<ShowTime> showTimes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(SHOWTIME_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(";");

                if (p.length == 7) {
                    showTimes.add(new ShowTime(
                            p[0],
                            p[1],
                            p[2],
                            p[3],
                            p[4],
                            p[5],
                            Double.parseDouble(p[6])
                    ));
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
                writer.write(showTime.getShowTimeId() + ";" +
                        showTime.getMovieId() + ";" +
                        showTime.getShowDate() + ";" +
                        showTime.getShowTime() + ";" +
                        showTime.getEndTime() + ";" +
                        showTime.getRoom() + ";" +
                        showTime.getBasePrice());
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Loi khi luu suat chieu");
        }
    }
    public void saveTicket(Ticket ticket) {
        try (FileWriter writer = new FileWriter(TICKET_FILE, true)) {
            writer.write(ticket.toFileString());
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Loi khi luu ve");
        }
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

    public boolean isSeatBooked(String showTimeId, String seatNumber) {
        for (String ticket : loadTicketLines()) {
            String[] p = ticket.split(";");

            if (p.length == 11 && p[6].equalsIgnoreCase(showTimeId) && p[8].equalsIgnoreCase(seatNumber)) {
                return true;
            }
        }

        return false;
    }
}