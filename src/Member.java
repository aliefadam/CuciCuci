
public class Member {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;
    
    public Member(String nama, String noHp, String id) {
        // TODO: buat constructor untuk class ini
        this.nama = nama;
        this.noHp = noHp;
        this.id = id;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    // mendeklarasikan method setter dan getter, yang intinya :
    // setter : digunakan untuk men-set nilai pada atribut class ini
    // getter : digunakan untuk mengambil nilai pada atribut class ini
    public String getNama() {
        return this.nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public String getNoHp() {
        return this.noHp;
    }
    
    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getBonusCounter() {
        return this.bonusCounter;
    }
    
    public void setBonusCounter(int bonusCounter) {
        this.bonusCounter = bonusCounter;
    } 
}
