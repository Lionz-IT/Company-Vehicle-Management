package lantara.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lantara.MainApp;
import lantara.model.PassengerCar;
import lantara.model.Truck;
import lantara.model.User;
import lantara.model.Vehicle;

import java.io.IOException;

public class MainViewController {

    // Komponen FXML untuk tabel
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

    // Komponen FXML untuk tombol-tombol
    @FXML
    private HBox buttonContainer;
    @FXML
    private Button addNewVehicleButton;

    // Variabel untuk data
    private final ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();
    private User currentUser;

    /**
     * Metode ini dipanggil secara otomatis setelah FXML dimuat.
     * Menginisialisasi tabel dan menyembunyikan tombol khusus manajer.
     */
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

        // Sembunyikan container tombol secara default
        if (buttonContainer != null) {
            buttonContainer.setVisible(false);
            buttonContainer.setManaged(false);
        }
    }

    /**
     * Menerima data pengguna dari LoginViewController dan mengatur UI sesuai peran.
     * @param user Pengguna yang berhasil login.
     */
    public void initData(User user) {
        this.currentUser = user;

        // Tampilkan tombol hanya jika peran pengguna adalah MANAJER
        if (currentUser != null && "MANAJER".equals(currentUser.getRole())) {
            if (buttonContainer != null) {
                buttonContainer.setVisible(true);
                buttonContainer.setManaged(true);
            }
        }
    }

    /**
     * Dipanggil saat tombol "Muat Ulang Data" ditekan.
     */
    @FXML
    protected void handleRefreshButton() {
        System.out.println("Tombol refresh ditekan!");
        loadVehicleData();
    }

    /**
     * Dipanggil saat tombol "Tambah Kendaraan Baru" ditekan.
     * Membuka jendela form tambah kendaraan.
     */
    @FXML
    protected void handleAddNewVehicle() {
        try {
            // 1. Muat file FXML untuk form
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/add-vehicle-view.fxml"));
            Parent root = loader.load();

            // 2. Dapatkan controller dari form dan berikan akses ke vehicleList
            AddVehicleController controller = loader.getController();
            controller.setVehicleList(vehicleList);

            // 3. Buat dan tampilkan jendela (Stage) baru sebagai dialog
            Stage stage = new Stage();
            stage.setTitle("Tambah Kendaraan Baru");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Blokir jendela utama
            stage.initOwner(vehicleTable.getScene().getWindow());
            stage.showAndWait(); // Tampilkan dan tunggu sampai ditutup

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metode untuk memuat data contoh kendaraan ke dalam tabel.
     */
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