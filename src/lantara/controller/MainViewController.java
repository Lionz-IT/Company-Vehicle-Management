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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
    private final String DATA_FILE = "vehicles.csv"; // Nama file untuk menyimpan data

    /**
     * Metode ini dipanggil secara otomatis setelah FXML dimuat.
     */
    @FXML
    public void initialize() {
        // Mengatur setiap kolom tabel
        colNomorPolisi.setCellValueFactory(new PropertyValueFactory<>("nomorPolisi"));
        colMerek.setCellValueFactory(new PropertyValueFactory<>("merek"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colTahun.setCellValueFactory(new PropertyValueFactory<>("tahun"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Memuat data dari file saat aplikasi dimulai
        loadDataFromFile();

        // Sembunyikan tombol khusus manajer secara default
        if (buttonContainer != null) {
            buttonContainer.setVisible(false);
            buttonContainer.setManaged(false);
        }
    }

    /**
     * Menerima data pengguna dari LoginViewController.
     */
    public void initData(User user) {
        this.currentUser = user;

        // Tampilkan tombol hanya jika peran adalah MANAJER
        if (currentUser != null && "MANAJER".equals(currentUser.getRole())) {
            if (buttonContainer != null) {
                buttonContainer.setVisible(true);
                buttonContainer.setManaged(true);
            }
        }
    }

    /**
     * Memuat ulang data dari file.
     */
    @FXML
    protected void handleRefreshButton() {
        System.out.println("Tombol refresh ditekan!");
        loadDataFromFile();
    }

    /**
     * Membuka jendela form tambah kendaraan.
     */
    @FXML
    protected void handleAddNewVehicle() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/add-vehicle-view.fxml"));
            Parent root = loader.load();
            AddVehicleController controller = loader.getController();
            controller.setVehicleList(vehicleList);

            Stage stage = new Stage();
            stage.setTitle("Tambah Kendaraan Baru");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(vehicleTable.getScene().getWindow());
            stage.showAndWait();

            // Setelah form ditutup, simpan data terbaru ke file
            saveDataToFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Menyimpan data dari vehicleList ke file vehicles.csv.
     */
    private void saveDataToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Vehicle vehicle : vehicleList) {
                StringBuilder line = new StringBuilder();
                line.append(vehicle.getNomorPolisi()).append(",");
                line.append(vehicle.getMerek()).append(",");
                line.append(vehicle.getModel()).append(",");
                line.append(vehicle.getTahun()).append(",");
                line.append(vehicle.getStatus());

                if (vehicle instanceof PassengerCar) {
                    PassengerCar car = (PassengerCar) vehicle;
                    line.append(",PASSENGER,").append(car.getKapasitasPenumpang());
                } else if (vehicle instanceof Truck) {
                    Truck truck = (Truck) vehicle;
                    line.append(",TRUCK,").append(truck.getKapasitasAngkutTon());
                }
                bw.write(line.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Memuat data dari file vehicles.csv ke dalam vehicleList.
     */
    private void loadDataFromFile() {
        vehicleList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String nopol = data[0];
                String merek = data[1];
                String model = data[2];
                int tahun = Integer.parseInt(data[3]);
                String status = data[4];
                String type = data[5];

                Vehicle vehicle = null;
                if ("PASSENGER".equals(type)) {
                    int kapasitas = Integer.parseInt(data[6]);
                    vehicle = new PassengerCar(nopol, merek, model, tahun, kapasitas);
                } else if ("TRUCK".equals(type)) {
                    double kapasitas = Double.parseDouble(data[6]);
                    vehicle = new Truck(nopol, merek, model, tahun, kapasitas);
                }
                
                if (vehicle != null) {
                    vehicle.updateStatus(status);
                    vehicleList.add(vehicle);
                }
            }
        } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("File data tidak ditemukan atau rusak, memulai dengan daftar kosong.");
        }
        vehicleTable.setItems(vehicleList);
    }
}