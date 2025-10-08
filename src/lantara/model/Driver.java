package lantara.model;

public class Driver {
    private String nomorIndukKaryawan; // Atribut dari UML [cite: 70]
    private String nama; // Atribut dari UML [cite: 71]
    private String nomorSIM; // Atribut dari UML [cite: 73]

    public Driver(String nomorIndukKaryawan, String nama, String nomorSIM) {
        this.nomorIndukKaryawan = nomorIndukKaryawan;
        this.nama = nama;
        this.nomorSIM = nomorSIM;
    }

    public void getDriverInfo() { // Method dari UML [cite: 74]
        System.out.println("Pengemudi: " + nama + " (NIK: " + nomorIndukKaryawan + ")");
    }

    public String getNama() {
        return nama;
    }
}