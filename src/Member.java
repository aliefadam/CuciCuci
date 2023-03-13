public class Member {
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;
    
    public Member(String nama, String noHp, String id) {
        this.nama = nama;
        this.noHp = noHp;
        this.id = id;
        this.bonusCounter = 0;
    }
    
    
    public String getNama() {
        return this.nama;
    }
    
    public String getNoHp() {
        return this.noHp;
    }
    
    public String getId() {
        return this.id;
    }
    
    public int getBonusCounter() {
        return this.bonusCounter;
    }
}
