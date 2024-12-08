package aplikasi.pemesanan.tiket.bioskop.kel06.auth;

import aplikasi.pemesanan.tiket.bioskop.kel06.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
            MainApp.showMainView();
        } else {
            System.out.println("Login failed!");
        }
    }

    @FXML
    private void switchToRegister() {
        MainApp.showRegisterView();
    }
}
