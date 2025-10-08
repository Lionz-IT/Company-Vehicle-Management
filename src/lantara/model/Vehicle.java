package lantara.model;

public class Vehicle {
    private String nomorPolisi;
    private String merek;
    private String model;
    private int tahun;
    private String status; // "Tersedia" atau "Digunakan"

    public Vehicle(String nomorPolisi, String merek, String model, int tahun) {
        this.nomorPolisi = nomorPolisi;
        this.merek = merek;
        this.model = model;
        this.tahun = tahun;
        this.status = "Tersedia"; // Status awal selalu tersedia [cite: 20]
    }

    // Method untuk menampilkan detail dasar kendaraan
    public void getDetails() {
        System.out.println("Nomor Polisi: " + nomorPolisi);
        System.out.println("Merek/Model: " + merek + " " + model + " (" + tahun + ")");
        System.out.println("Status: " + status);
    }

    // Method untuk mengubah status kendaraan
    public void updateStatus(String newStatus) { // Sesuai UML [cite: 68]
        this.status = newStatus;
    }

    public String getNomorPolisi() {
        return nomorPolisi;
    }

    public String getStatus() {
        return status;
    }
}