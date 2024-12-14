/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasi.pemesanan.tiket.bioskop.kel06;

/**
 *
 * @author felix
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieController {
    // Method untuk mendapatkan kursi yang tersedia
    public List<Seat> getAvailableSeats(int movieId) throws SQLException {
        List<Seat> availableSeats = new ArrayList<>();
        String query = "SELECT * FROM Seats WHERE movie_id = ? AND is_booked = FALSE ORDER BY seat_number";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    availableSeats.add(new Seat(
                        rs.getInt("id"),
                        rs.getInt("movie_id"),
                        rs.getString("seat_number"),
                        rs.getBoolean("is_booked")
                    ));
                }
            }
        }
        return availableSeats;
    }
    public void bookSeats(List<Integer> seatIds) throws SQLException {
    String query = "UPDATE Seats SET is_booked = TRUE WHERE id = ?";

    try (Connection connection = DatabaseUtil.getConnection();
         PreparedStatement pstmt = connection.prepareStatement(query)) {

        for (int seatId : seatIds) {
            pstmt.setInt(1, seatId);
            pstmt.executeUpdate();
        }
    }
}

    public void addMovie(String title, String schedule, double price) throws SQLException {
        String query = "INSERT INTO Movies (title, schedule, price) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, title);
            pstmt.setString(2, schedule);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
        }
    }

    public void deleteMovie(int id) throws SQLException {
        String query = "DELETE FROM Movies WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}

