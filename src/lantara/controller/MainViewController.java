package lantara.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lantara.model.PassengerCar;
import lantara.model.Truck;
import lantara.model.Vehicle;

public class MainViewController {

    // Anotasi @FXML menghubungkan variabel ini dengan komponen di FXML
    @FXML
    private TableView<Vehicle> vehicleTable;
    @FXML
    private TableColumn<Vehicle, String> colNomorPolisi;
    @FXML
    private TableColumn<Vehicle, String> colMerek;
    @FXML
    private TableColumn<Vehicle, String> colModel;
    @FXML
    private TableColumn<Vehicle, Integer> colTahun;
    @FXML
    private TableColumn<Vehicle, String> colStatus;

    private final ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();

    // Metode ini akan dipanggil secara otomatis setelah FXML dimuat
    @FXML
    public void initialize() {
        // Mengatur setiap kolom tabel untuk mengambil data dari atribut kelas Vehicle
        colNomorPolisi.setCellValueFactory(new PropertyValueFactory<>("nomorPolisi"));
        colMerek.setCellValueFactory(new PropertyValueFactory<>("merek"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colTahun.setCellValueFactory(new PropertyValueFactory<>("tahun"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Memuat data awal saat aplikasi pertama kali dijalankan
        loadVehicleData();
    }

    // Metode ini dipanggil saat tombol "Muat Ulang Data" ditekan
    @FXML
    protected void handleRefreshButton() {
        System.out.println("Tombol refresh ditekan!");
        loadVehicleData();
    }

    // Metode untuk memuat data kendaraan (diambil dari Main lama Anda)
    private void loadVehicleData() {
        // Membersihkan data lama sebelum memuat yang baru
        vehicleList.clear();

        // Menambahkan data contoh
        vehicleList.add(new PassengerCar("L 1234 AB", "Toyota", "Avanza", 2022, 7));
        vehicleList.add(new Truck("B 9876 CD", "Mitsubishi", "Fuso", 2020, 10));
        vehicleList.add(new PassengerCar("W 5555 XY", "Honda", "Brio", 2023, 5));

        // Menampilkan data ke tabel
        vehicleTable.setItems(vehicleList);
    }
}