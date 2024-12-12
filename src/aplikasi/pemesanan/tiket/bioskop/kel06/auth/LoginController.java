package aplikasi.pemesanan.tiket.bioskop.kel06.auth;

import aplikasi.pemesanan.tiket.bioskop.kel06.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private UserController userController = new UserController();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userController.login(username, password)) {
            System.out.println("Login successful!");
            MainApp.showMainView(); // Panggil fungsi untuk menampilkan main view
        } else {
            System.out.println("Login failed!");
        }
    }

    @FXML
    private void switchToRegister() {
        MainApp.showRegisterView(); // Panggil fungsi untuk menampilkan register view
    }

    /**
     * Method untuk memuat tampilan login dan menambahkan styles.css ke Scene.
     */
    public static void showLogin(Stage primaryStage) {
        try {
            // Muat file FXML
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("login.fxml"));
            Parent root = loader.load();

            // Buat Scene dan tambahkan file CSS
            Scene scene = new Scene(root);
            scene.getStylesheets().add(LoginController.class.getResource("styles.css").toExternalForm());

            // Atur Stage
            primaryStage.setTitle("Login Page");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
