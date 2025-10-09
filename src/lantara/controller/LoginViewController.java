package lantara.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lantara.MainApp;
import lantara.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginViewController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorMessageLabel;

    private List<User> users;

    public LoginViewController() {
        users = new ArrayList<>();
        // Tambahkan peran "MANAJER" atau "STAF"
        users.add(new User("manajer", "manajer123", "MANAJER"));
        users.add(new User("staf", "staf123", "STAF"));
    }

    @FXML
    protected void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User authenticatedUser = authenticate(username, password);

        if (authenticatedUser != null) {
            // Jika berhasil, kirim data user ke jendela utama
            closeLoginWindow();
            openMainWindow(authenticatedUser); // Kirim objek User
        } else {
            errorMessageLabel.setText("Username atau password salah!");
        }
    }

    // Mengembalikan objek User jika berhasil, atau null jika gagal
    private User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user; // Kembalikan objek User yang cocok
            }
        }
        return null; // Gagal otentikasi
    }

    // Menerima objek User untuk dikirim ke controller utama
    private void openMainWindow(User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);

            // Ambil controller dari jendela utama
            MainViewController mainViewController = fxmlLoader.getController();
            // Kirim data user ke MainViewController
            mainViewController.initData(user);

            Stage mainStage = new Stage();
            mainStage.setTitle("LANTARA - Manajemen Armada");
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeLoginWindow() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }
}