package aplikasi.pemesanan.tiket.bioskop.kel06.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    private Connection connect() {
        // MySQL connection string
        String url = "jdbc:mysql://localhost:3306/tiketbioskop";
        String user = "root"; // Change to your database user
        String password = ""; // Change to your database password
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // Disable auto-commit
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return conn;
    }

    public boolean register(String username, String password, String email) {
        String sql = "INSERT INTO Users(username, password, email) VALUES(?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            conn.commit(); // Commit the transaction
            System.out.println("User registered successfully!");
            return true;
        } catch (SQLException e) {
            System.out.println("Registration failed!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Login failed!");
            e.printStackTrace();
            return false;
        }
    }
}
