package lantara;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Mengubah agar memuat tampilan login terlebih dahulu
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300); // Sesuaikan ukuran jendela login
        stage.setTitle("Login - LANTARA"); // Ganti judul jendela
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}