
public class NotaTemp {

    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private int idNota;
    private String paket;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady = false;
    MemberTemp member;

    public NotaTemp(MemberTemp member, int idNota, String paket, int berat, String tanggalMasuk, int sisaHariPengerjaan) {
        // TODO: buat constructor untuk class ini
        this.idNota = idNota;
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.sisaHariPengerjaan = sisaHariPengerjaan;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
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

    public MemberTemp getMember() {
        return this.member;
    }

    public void setMember(MemberTemp member) {
        this.member = member;
    }

}
