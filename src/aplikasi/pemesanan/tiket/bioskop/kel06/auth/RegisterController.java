package aplikasi.pemesanan.tiket.bioskop.kel06.auth;

import aplikasi.pemesanan.tiket.bioskop.kel06.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;

    private UserController userController = new UserController();

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        if (userController.register(username, password, email)) {
            System.out.println("Registration successful!");
            MainApp.showLoginView();
        } else {
            System.out.println("Registration failed!");
        }
    }

    @FXML
    private void switchToLogin() {
        MainApp.showLoginView();
    }
}
