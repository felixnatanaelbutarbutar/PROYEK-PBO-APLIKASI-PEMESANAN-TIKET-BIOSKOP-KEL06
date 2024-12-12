/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aplikasi.pemesanan.tiket.bioskop.kel06;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author felix
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLDocumentController {

    @FXML
    private TableView<Movie> movieTable;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, String> scheduleColumn;

    @FXML
    private TableColumn<Movie, Double> priceColumn;

    @FXML
    private TextField titleField;

    @FXML
    private TextField scheduleField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField ticketCountField;

    @FXML
    private Button addMovieButton;

    @FXML
    private Button updateMovieButton;

    @FXML
    private Button deleteMovieButton;

    @FXML
    private Button bookTicketButton;

    @FXML
    private Label totalCostLabel;

    @FXML
    private Label bookingInfoLabel;

    private ObservableList<Movie> movieList;

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        scheduleColumn.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        movieList = FXCollections.observableArrayList();
        loadMovies();
        movieTable.setItems(movieList);

        addMovieButton.setOnAction(event -> addMovie());
        updateMovieButton.setOnAction(event -> updateMovie());
        deleteMovieButton.setOnAction(event -> deleteMovie());
        bookTicketButton.setOnAction(event -> bookTicket());

        movieTable.setOnMouseClicked(this::populateFields);
    }

    private void loadMovies() {
        movieList.clear();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM Movies";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                movieList.add(new Movie(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("schedule"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("capacity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addMovie() {
        String title = titleField.getText();
        String schedule = scheduleField.getText();
        double price;

        try {
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Harga tiket harus berupa angka!");
            return;
        }

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO Movies (title, schedule, price) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, schedule);
            statement.setDouble(3, price);
            statement.executeUpdate();

            loadMovies();
            clearFields();
            showAlert("Success", "Film berhasil ditambahkan.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateMovie() {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            showAlert("Error", "Pilih film yang ingin diperbarui!");
            return;
        }

        String title = titleField.getText();
        String schedule = scheduleField.getText();
        double price;

        try {
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Harga tiket harus berupa angka!");
            return;
        }

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "UPDATE Movies SET title = ?, schedule = ?, price = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, schedule);
            statement.setDouble(3, price);
            statement.setInt(4, selectedMovie.getId());
            statement.executeUpdate();

            loadMovies();
            clearFields();
            showAlert("Success", "Film berhasil diperbarui.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteMovie() {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            showAlert("Error", "Pilih film yang ingin dihapus!");
            return;
        }

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "DELETE FROM Movies WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, selectedMovie.getId());
            statement.executeUpdate();

            loadMovies();
            clearFields();
            showAlert("Success", "Film berhasil dihapus.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void bookTicket() {
    Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
    if (selectedMovie == null) {
        showAlert("Error", "Pilih film terlebih dahulu!");
        return;
    }

    try {
        int ticketCount = Integer.parseInt(ticketCountField.getText());
        if (ticketCount <= 0) throw new NumberFormatException();

        try (Connection connection = DatabaseUtil.getConnection()) {
            // Cek jumlah tiket yang sudah dipesan untuk film ini
            String checkCapacityQuery = "SELECT SUM(ticket_count) AS total_tickets FROM Bookings WHERE movie_id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkCapacityQuery);
            checkStatement.setInt(1, selectedMovie.getId());
            ResultSet resultSet = checkStatement.executeQuery();

            int totalTicketsBooked = 0;
            if (resultSet.next()) {
                totalTicketsBooked = resultSet.getInt("total_tickets");
            }

            int remainingTickets = selectedMovie.getCapacity() - totalTicketsBooked;
            if (ticketCount > remainingTickets) {
                showAlert("Error", "Tiket tidak cukup tersedia! Sisa tiket: " + remainingTickets);
                return;
            }

            // Lanjutkan dengan pemesanan jika kapasitas mencukupi
            double totalPrice = selectedMovie.getPrice() * ticketCount;

            // Generate nomor pemesanan dan nomor kursi
            String bookingNumber = generateBookingNumber();
            String seatNumbers = generateSeatNumbers(ticketCount);

            String insertQuery = "INSERT INTO Bookings (booking_number, movie_id, ticket_count, total_price, seat_numbers) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, bookingNumber);
            insertStatement.setInt(2, selectedMovie.getId());
            insertStatement.setInt(3, ticketCount);
            insertStatement.setDouble(4, totalPrice);
            insertStatement.setString(5, seatNumbers);
            insertStatement.executeUpdate();

            totalCostLabel.setText("Total Harga: Rp" + totalPrice);
            bookingInfoLabel.setText("Tiket berhasil dipesan!\nNomor Pemesanan: " + bookingNumber + "\nNomor Kursi: " + seatNumbers);
        }
    } catch (NumberFormatException e) {
        showAlert("Error", "Masukkan jumlah tiket yang valid!");
    } catch (SQLException e) {
        showAlert("Error", "Gagal memesan tiket: " + e.getMessage());
        e.printStackTrace();
    }
}

    private String generateBookingNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String datePart = dateFormat.format(new Date());
        String randomPart = generateRandomString(4);
        return datePart + "-" + randomPart;
    }
    private String generateSeatNumbers(int ticketCount) {
        List<String> seatNumbers = new ArrayList<>();
        String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int seatNumberInRow = 1;
        int rowIndex = 0;

        for (int i = 0; i < ticketCount; i++) {
            if (seatNumberInRow > 10) {
                rowIndex++;
                seatNumberInRow = 1;
            }

            if (rowIndex >= rows.length) {
                throw new IllegalArgumentException("Tidak cukup kursi tersedia");
            }

            seatNumbers.add(rows[rowIndex] + seatNumberInRow);
            seatNumberInRow++;
        }

        return String.join(", ", seatNumbers);
    }

    
    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }

        return randomString.toString();
    }
    public String bookNextAvailableSeat(int movieId) throws SQLException {
    String query = "SELECT seat_number FROM Seats WHERE movie_id = ? AND is_booked = false ORDER BY seat_number LIMIT 1";
    String updateQuery = "UPDATE Seats SET is_booked = true WHERE movie_id = ? AND seat_number = ?";
    String bookedSeat = null;

    try (Connection connection = DatabaseUtil.getConnection();
         PreparedStatement selectStmt = connection.prepareStatement(query);
         PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

        // Cari kursi yang tersedia
        selectStmt.setInt(1, movieId);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            bookedSeat = rs.getString("seat_number");

            // Tandai kursi sebagai terpesan
            updateStmt.setInt(1, movieId);
            updateStmt.setString(2, bookedSeat);
            updateStmt.executeUpdate();
        }
    }

    return bookedSeat;
}

    @FXML
    private void populateFields(MouseEvent event) {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            titleField.setText(selectedMovie.getTitle());
            scheduleField.setText(selectedMovie.getSchedule());
            priceField.setText(String.valueOf(selectedMovie.getPrice()));
        }
    }

    private void clearFields() {
        titleField.clear();
        scheduleField.clear();
        priceField.clear();
        ticketCountField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

