/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasi.pemesanan.tiket.bioskop.kel06;

/**
 *
 * @author felix
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingController {
    public void bookTicket(Movie movie, int ticketCount) throws SQLException {
        String query = "INSERT INTO Bookings (movie_id, ticket_count, total_price) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, movie.getId());
            pstmt.setInt(2, ticketCount);
            pstmt.setDouble(3, movie.getPrice() * ticketCount);
            pstmt.executeUpdate();
        }
    }
}
