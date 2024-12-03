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
                        resultSet.getDouble("price")
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

            double totalPrice = selectedMovie.getPrice() * ticketCount;

            try (Connection connection = DatabaseUtil.getConnection()) {
                String query = "INSERT INTO Bookings (movie_id, ticket_count, total_price) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, selectedMovie.getId());
                statement.setInt(2, ticketCount);
                statement.setDouble(3, totalPrice);
                statement.executeUpdate();
            }

            totalCostLabel.setText("Total Harga: Rp" + totalPrice);
            bookingInfoLabel.setText("Tiket berhasil dipesan!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Masukkan jumlah tiket yang valid!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

