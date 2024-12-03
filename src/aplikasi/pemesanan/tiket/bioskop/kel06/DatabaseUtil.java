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
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    // Properti untuk koneksi database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tiketbioskop"; // Tambahkan port (default: 3306)
    private static final String DB_USER = "root"; // Username MySQL Anda
    private static final String DB_PASSWORD = ""; // Password MySQL Anda (biarkan kosong jika tidak ada)

    // Metode untuk mendapatkan koneksi database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
    