package com.nhom6.api;

import com.nhom6.controller.MovieController;
import com.nhom6.controller.ShowTimeController;
import com.nhom6.controller.TicketController;
import com.nhom6.model.Movie;
import com.nhom6.model.ShowTime;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

public class ApiServer {
    private static MovieController movieController = new MovieController();
    private static ShowTimeController showTimeController = new ShowTimeController();
    private static TicketController ticketController = new TicketController();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", ApiServer::handleHome);
        server.createContext("/movies", ApiServer::handleMovies);
        server.createContext("/showtimes", ApiServer::handleShowTimes);
        server.createContext("/tickets", ApiServer::handleTickets);

        server.setExecutor(null);
        server.start();

        System.out.println("API Server dang chay tai http://localhost:8080");
        System.out.println("GET http://localhost:8080/movies");
        System.out.println("GET http://localhost:8080/showtimes");
        System.out.println("GET http://localhost:8080/tickets");
    }

    private static void handleHome(HttpExchange exchange) throws IOException {

        String html =
                "<html>" +
                        "<head><title>Cinema API</title></head>" +
                        "<body style='font-family:Arial;text-align:center;margin-top:80px'>" +

                        "<h2>CINEMA BOOKING API</h2>" +

                        "<br>" +

                        "<button onclick=\"location.href='/movies'\" " +
                        "style='width:220px;height:50px;font-size:18px'>Danh sach phim</button>" +

                        "<br><br>" +

                        "<button onclick=\"location.href='/showtimes'\" " +
                        "style='width:220px;height:50px;font-size:18px'>Danh sach suat chieu</button>" +

                        "<br><br>" +

                        "<button onclick=\"location.href='/tickets'\" " +
                        "style='width:220px;height:50px;font-size:18px'>Danh sach ve</button>" +

                        "</body>" +
                        "</html>";

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");

        byte[] bytes = html.getBytes("UTF-8");

        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private static void handleMovies(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        List<Movie> movies = movieController.getAllMovies();
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);

            json.append("{")
                    .append("\"movieId\":\"").append(movie.getMovieId()).append("\",")
                    .append("\"movieName\":\"").append(movie.getMovieName()).append("\",")
                    .append("\"genre\":\"").append(movie.getGenre()).append("\",")
                    .append("\"duration\":").append(movie.getDuration()).append(",")
                    .append("\"ageLimit\":").append(movie.getAgeLimit()).append(",")
                    .append("\"status\":\"").append(movie.getStatus()).append("\"")
                    .append("}");

            if (i < movies.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        sendJson(exchange, 200, json.toString());
    }

    private static void handleShowTimes(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        List<ShowTime> showTimes = showTimeController.getAllShowTimes();
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < showTimes.size(); i++) {
            ShowTime showTime = showTimes.get(i);

            json.append("{")
                    .append("\"showTimeId\":\"").append(showTime.getShowTimeId()).append("\",")
                    .append("\"movieId\":\"").append(showTime.getMovieId()).append("\",")
                    .append("\"showDate\":\"").append(showTime.getShowDate()).append("\",")
                    .append("\"startTime\":\"").append(showTime.getShowTime()).append("\",")
                    .append("\"endTime\":\"").append(showTime.getEndTime()).append("\",")
                    .append("\"room\":\"").append(showTime.getRoom()).append("\",")
                    .append("\"basePrice\":").append(showTime.getBasePrice())
                    .append("}");

            if (i < showTimes.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        sendJson(exchange, 200, json.toString());
    }

    private static void handleTickets(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        List<String> tickets = ticketController.getAllTicketLines();
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < tickets.size(); i++) {
            String[] p = tickets.get(i).split(";");

            if (p.length == 11) {
                json.append("{")
                        .append("\"ticketId\":\"").append(p[0]).append("\",")
                        .append("\"customerName\":\"").append(p[1]).append("\",")
                        .append("\"phone\":\"").append(p[2]).append("\",")
                        .append("\"email\":\"").append(p[3]).append("\",")
                        .append("\"movieId\":\"").append(p[4]).append("\",")
                        .append("\"movieName\":\"").append(p[5]).append("\",")
                        .append("\"showTimeId\":\"").append(p[6]).append("\",")
                        .append("\"time\":\"").append(p[7]).append("\",")
                        .append("\"seat\":\"").append(p[8]).append("\",")
                        .append("\"price\":").append(p[9]).append(",")
                        .append("\"paymentStatus\":\"").append(p[10]).append("\"")
                        .append("}");

                if (i < tickets.size() - 1) {
                    json.append(",");
                }
            }
        }

        json.append("]");
        sendJson(exchange, 200, json.toString());
    }

    private static void sendJson(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        sendResponse(exchange, statusCode, response);
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] bytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}