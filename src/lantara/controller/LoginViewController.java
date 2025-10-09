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
        // Inisialisasi daftar pengguna.
        // PENTING: Dalam aplikasi nyata, data ini harus berasal dari database
        // dan password harus di-hash, bukan teks biasa.
        users = new ArrayList<>();
        users.add(new User("manajer", "manajer123"));
        users.add(new User("staf", "staf123"));
    }

    @FXML
    protected void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            // Jika berhasil, tutup jendela login dan buka jendela utama
            closeLoginWindow();
            openMainWindow();
        } else {
            // Jika gagal, tampilkan pesan error
            errorMessageLabel.setText("Username atau password salah!");
        }
    }

    private boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Pengguna ditemukan dan password cocok
            }
        }
        return false; // Gagal otentikasi
    }

    private void openMainWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
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