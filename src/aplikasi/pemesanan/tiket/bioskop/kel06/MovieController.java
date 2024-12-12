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
    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM Movies";

        try (Connection connection = DatabaseUtil.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                movies.add(new Movie(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("schedule"),
                    rs.getDouble("price"),
                    rs.getInt("capacity")
                ));
            }
        }

        return movies;
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

