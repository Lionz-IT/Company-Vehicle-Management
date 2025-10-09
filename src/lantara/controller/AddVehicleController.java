package lantara.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lantara.model.PassengerCar;
import lantara.model.Truck;
import lantara.model.Vehicle;

public class AddVehicleController {

    @FXML private TextField nopolField;
    @FXML private TextField merekField;
    @FXML private TextField modelField;
    @FXML private TextField tahunField;
    @FXML private ChoiceBox<String> jenisChoiceBox;
    @FXML private VBox kapasitasPenumpangBox;
    @FXML private TextField penumpangField;
    @FXML private VBox kapasitasAngkutBox;
    @FXML private TextField angkutField;
    @FXML private Label errorLabel;
    @FXML private Button saveButton;

    private ObservableList<Vehicle> vehicleList;

    // Metode ini akan dipanggil saat FXML dimuat
    @FXML
    public void initialize() {
        jenisChoiceBox.getItems().addAll("Mobil Penumpang", "Truk");
        // Tambahkan listener untuk menampilkan field yang sesuai
        jenisChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            kapasitasPenumpangBox.setVisible("Mobil Penumpang".equals(newVal));
            kapasitasPenumpangBox.setManaged("Mobil Penumpang".equals(newVal));
            kapasitasAngkutBox.setVisible("Truk".equals(newVal));
            kapasitasAngkutBox.setManaged("Truk".equals(newVal));
        });
    }
    
    // Metode ini dipanggil dari MainViewController untuk memberikan akses ke daftar kendaraan
    public void setVehicleList(ObservableList<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    @FXML
    private void handleSaveButton() {
        // Validasi input dasar
        if (nopolField.getText().isEmpty() || merekField.getText().isEmpty() || jenisChoiceBox.getValue() == null) {
            errorLabel.setText("Nomor Polisi, Merek, dan Jenis harus diisi!");
            return;
        }

        try {
            String nopol = nopolField.getText();
            String merek = merekField.getText();
            String model = modelField.getText();
            int tahun = Integer.parseInt(tahunField.getText());

            Vehicle newVehicle = null;
            if ("Mobil Penumpang".equals(jenisChoiceBox.getValue())) {
                int kapasitas = Integer.parseInt(penumpangField.getText());
                newVehicle = new PassengerCar(nopol, merek, model, tahun, kapasitas);
            } else if ("Truk".equals(jenisChoiceBox.getValue())) {
                double kapasitas = Double.parseDouble(angkutField.getText());
                newVehicle = new Truck(nopol, merek, model, tahun, kapasitas);
            }
            
            // Tambahkan kendaraan baru ke daftar
            if (newVehicle != null) {
                vehicleList.add(newVehicle);
                closeWindow();
            }

        } catch (NumberFormatException e) {
            errorLabel.setText("Tahun atau kapasitas harus berupa angka!");
        }
    }

    @FXML
    private void handleCancelButton() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nopolField.getScene().getWindow();
        stage.close();
    }
}