package lantara.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import lantara.model.PassengerCar;
import lantara.model.Truck;
import lantara.model.User;
import lantara.model.Vehicle;

public class MainViewController {

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

    // Variabel baru untuk mengontrol tombol
    @FXML
    private HBox buttonContainer;
    @FXML
    private Button addNewVehicleButton;

    private final ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();
    private User currentUser; // Variabel untuk menyimpan user yang login

    @FXML
    public void initialize() {
        colNomorPolisi.setCellValueFactory(new PropertyValueFactory<>("nomorPolisi"));
        colMerek.setCellValueFactory(new PropertyValueFactory<>("merek"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colTahun.setCellValueFactory(new PropertyValueFactory<>("tahun"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadVehicleData();
        
        // Sembunyikan tombol secara default saat FXML pertama dimuat
        if (buttonContainer != null) {
            buttonContainer.setVisible(false);
            buttonContainer.setManaged(false);
        }
    }

    // Metode baru untuk menerima data dari LoginViewController
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

    @FXML
    protected void handleRefreshButton() {
        System.out.println("Tombol refresh ditekan!");
        loadVehicleData();
    }
    
    @FXML
    protected void handleAddNewVehicle() {
        System.out.println("Tombol 'Tambah Kendaraan Baru' diklik oleh " + currentUser.getUsername());
        // Logika untuk membuka jendela tambah kendaraan baru
    }

    private void loadVehicleData() {
        vehicleList.clear();
        vehicleList.add(new PassengerCar("L 1234 AB", "Toyota", "Avanza", 2022, 7));
        vehicleList.add(new Truck("B 9876 CD", "Mitsubishi", "Fuso", 2020, 10));
        vehicleList.add(new PassengerCar("W 5555 XY", "Honda", "Brio", 2023, 5));
        vehicleTable.setItems(vehicleList);
    }
}