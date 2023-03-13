
public class Nota {

    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private int idNota;
    private String paket;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady = false;
    Member member;

    public Nota(Member member, int idNota, String paket, int berat, String tanggalMasuk, int sisaHariPengerjaan) {
        // TODO: buat constructor untuk class ini
        this.idNota = idNota;
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.sisaHariPengerjaan = sisaHariPengerjaan;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    // mendeklarasikan method setter dan getter, yang intinya :
    // setter : digunakan untuk men-set nilai pada atribut class ini
    // getter : digunakan untuk mengambil nilai pada atribut class ini
    public int getIdNota() {
        return this.idNota;
    }

    void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public String getPaket() {
        return this.paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public int getBerat() {
        return this.berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public String getTanggalMasuk() {
        return this.tanggalMasuk;
    }

    public void setTanggalMasuk(String tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public int getSisaHariPengerjaan() {
        return this.sisaHariPengerjaan;
    }

    public void setSisaHariPengerjaan(int sisaHariPengerjaan) {
        if (this.sisaHariPengerjaan == 0) {
            this.sisaHariPengerjaan = 0;
        } else {
            this.sisaHariPengerjaan -= sisaHariPengerjaan;
        }
    }

    public boolean getIsReady() {
        if (this.sisaHariPengerjaan == 0) {
            this.isReady = true;
        }
        return this.isReady;
    }

    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
