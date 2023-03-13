
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Nota {
    private int idNota;
    private String paket;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady;
    private String status;
    Member member;
    
    public Nota(int idNota, String paket, int  berat, String tanggalMasuk, int sisaHariPengerjaan, boolean isReady, Member member, String status) {
        this.idNota = idNota;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.sisaHariPengerjaan = sisaHariPengerjaan;
        this.isReady = isReady;
        this.member = member;
        this.status = status;
    }
    
    public int getIdNota() {
        return this.idNota;
    }
    public String getPaket() {
        return this.paket;
    }
    public int getBerat() {
        return this.berat;
    }
    public String getTanggalMasuk() {
        return this.tanggalMasuk;
    }
    public int getSisaHariPengerjaan() {
        return this.sisaHariPengerjaan;
    }
    public boolean getIsReady() {
        return this.isReady;
    }
    public Member getMember() {
        return this.member;
    }
    public String getStatus() {
        return this.status;
    }
    
    

}
