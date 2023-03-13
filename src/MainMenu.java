
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class MainMenu {

    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();

    // arraylist bertipe object yang diambil dari 2 class yang ada
    private static ArrayList<Member> memberList = new ArrayList<Member>();
    private static ArrayList<Nota> notaList = new ArrayList<Nota>();

    // variabel idNota digunakan untuk menset nilai awal idNota = 0
    private static int idNota = 0;

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            // menampilkan menu, memanggil method printMenu()
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command) {
                case "1":
                    // memanggil fungsi untuk membuat member baru
                    handleGenerateUser();
                    break;
                case "2":
                    // memanggil fungsi untuk membuat nota berdasarkan member
                    handleGenerateNota();
                    break;
                case "3":
                    // memanggil fungsi untuk menampilkan daftar nota
                    handleListNota();
                    break;
                case "4":
                    // memanggil fungsi untuk menampilkan daftar member
                    handleListUser();
                    break;
                case "5":
                    // memanggil fungsi untuk menghandle pengambilan cucian
                    handleAmbilCucian();
                    break;
                case "6":
                    // memanggil fungsi untuk menambah hari 1
                    handleNextDay();
                    break;
                case "0":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    // Method untuk menangani pembuatan user/member baru
    private static void handleGenerateUser() {
        // TODO: handle generate user
        // Meminta input nama dari user
        System.out.println("Masukkan nama Anda: ");
        String nama = input.nextLine();
        // Meminta input nomor handphone dari user
        System.out.println("Masukkan nomor handphone Anda: ");
        String nomorHp = input.nextLine();

        // Memeriksa apakah user dengan nama dan nomor hp tersebut sudah terdaftar sebelumnya
        for (Member member : memberList) {
            if (nama.equals(member.getNama()) && nomorHp.equals(member.getNoHp())) {
                System.out.println("Member dengan nama " + nama + " dan nomor hp " + nomorHp + " sudah ada!");
                return;
            }
        }

        // Membuat ID baru untuk user dengan memanggil method generateId()
        String idNota = generateId(nama, nomorHp);

        // Membuat objek member baru dan menambahkannya ke list memberList
        Member member = new Member(nama, nomorHp, idNota);
        memberList.add(member);

        // Menampilkan pesan sukses dengan ID user yang baru dibuat
        System.out.println("Berhasil membuat member dengan ID " + idNota + "!");
    }

    // Method untuk menangani pembuatan nota dari member berdasarkan ID member
    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
        System.out.println("Masukkan ID member: "); // Meminta input ID member
        String idMember = input.nextLine(); // Menyimpan input ID member ke dalam variabel idMember
        for (Member member : memberList) { // looping untuk mencari member berdasarkan idMember
            if (idMember.equals(member.getId())) { // jika idMember ditemukan dalam list member
                String paket = "";
                do { // looping untuk validasi jenis paket
                    System.out.print("Masukkan paket laundry: ");
                    paket = input.nextLine().toLowerCase(); // meminta input jenis paket dan mengkonversi menjadi lowercase
                    if (paket.equals("?")) {
                        showPaket();  // menampilkan jenis paket jika input "?" 
                    } else if (!paket.equals("express") && !paket.equals("fast") && !paket.equals("reguler")) {   // jika input paket tidak valid
                        System.out.println("Paket " + paket + " tidak diketahui.");
                        System.out.println("[Ketik ? untuk mencari tahu jenis paket.]");
                    }
                } while (!paket.equals("express") && !paket.equals("fast") && !paket.equals("reguler")); // looping terus berlanjut jika paket tidak valid

                // validasi input berat cucian
                System.out.print("Masukkan berat cucian Anda [Kg]: ");  // meminta input berat cucian
                String berat = input.nextLine();  // menyimpan input berat cucian ke dalam variabel berat
                while (!berat.matches("[0-9]+")) {  // validasi input harus berupa bilangan positif
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                    System.out.print("Masukkan berat cucian Anda [Kg]: ");
                    berat = input.nextLine();
                }
                int sisaHariPengerjaan = sisaHariPengerjaan(paket, fmt.format(cal.getTime()));  // menghitung sisa hari pengerjaan
                String notaStruk = generateNota(idMember, paket, Integer.parseInt(berat), fmt.format(cal.getTime()));  // membuat struk nota
                Nota nota = new Nota(member, idNota, paket, Integer.parseInt(berat), fmt.format(cal.getTime()), sisaHariPengerjaan);  // membuat objek nota
                notaList.add(nota);  // menambahkan nota ke dalam list notaList
                System.out.println(notaStruk);  // menampilkan struk nota
                idNota++;  // increment id nota
                return;
            }
        }
        System.out.println("Member dengan ID " + idMember + " tidak ditemukan!");  // Menampilkan pesan jika member tidak ditemukan dalam list member
    }

    // Fungsi untuk menampilkan daftar seluruh nota yang terdaftar pada sistem
    private static void handleListNota() {
        // TODO: Menghandle daftar semua nota pada sistem
        System.out.println("Terdaftar " + notaList.size() + " nota dalam sistem"); // mencetak jumlah nota yang terdaftar pada sistem

        if (!notaList.isEmpty()) { // jika daftar nota tidak kosong, maka cetak setiap nota pada daftar
            for (Nota nota : notaList) {
                boolean isReady = nota.getIsReady(); // cek apakah nota sudah siap atau belum
                String status = "";
                if (isReady) {
                    status = "Sudah dapat diambil"; // jika nota sudah siap, maka statusnya bisa diambil
                } else {
                    status = "Belum bisa diambil :("; // jika nota belum siap, maka statusnya belum bisa diambil
                }
                System.out.println("- [" + nota.getIdNota() + "] Status : " + status); // mencetak id nota dan statusnya
            }
        }
    }

    // Fungsi untuk menampilkan daftar seluruh member yang terdaftar pada sistem
    private static void handleListUser() {
        // TODO: handle list semua user pada sistem
        System.out.println("Terdaftar " + memberList.size() + " member dalam sistem.");// Jika daftar member tidak kosong
        if (!memberList.isEmpty()) {
            // Looping untuk menampilkan informasi tiap member
            for (Member member : memberList) {
                System.out.println("- " + member.getId() + " : " + member.getNama());
            }
        }
    }

    // Method untuk mengambil cucian dari sistem
    private static void handleAmbilCucian() {
        System.out.println("Masukkan ID nota yang akan diambil: ");
        String idNota = input.nextLine();
        // Validasi input ID nota harus berupa angka
        while (!idNota.matches("[0-9]+")) {
            System.out.println("ID nota berbentuk angka!");
            System.out.println("Masukkan ID nota yang akan diambil: ");
            idNota = input.nextLine();
        }
        // Iterasi semua nota pada sistem untuk mencari nota dengan ID yang diinputkan
        for (int i = 0; i < notaList.size(); i++) {
            if (notaList.get(i).getIdNota() == Integer.parseInt(idNota)) {
                // Jika sisa hari pengerjaan 0, maka nota dapat diambil dan dihapus dari sistem
                if (notaList.get(i).getSisaHariPengerjaan() == 0) {
                    notaList.remove(i);
                    System.out.println("Nota dengan ID " + idNota + " berhasil diambil!");
                    return;
                } else {
                    // Jika sisa hari pengerjaan lebih dari 0, maka nota belum dapat diambil
                    System.out.println("Nota dengan ID " + idNota + " gagal diambil!");
                    return;
                }
            }
        }
        // Jika nota dengan ID yang diinputkan tidak ditemukan, maka cetak pesan
        System.out.println("Nota dengan ID " + idNota + " tidak ditemukan!");
    }

    // Method untuk mengganti hari pada sistem
    private static void handleNextDay() {
        // Tambahkan 1 hari ke dalam calendar
        cal.add(Calendar.DAY_OF_MONTH, 1);
        // Kurangi sisa hari pengerjaan pada setiap nota dengan 1 hari
        for (Nota nota : notaList) {
            nota.setSisaHariPengerjaan(1);
        }
        System.out.println("Dek Depe tidur hari ini... zzz...");
        // Cek apakah setiap nota pada sistem sudah siap diambil, jika iya cetak pesan
        for (Nota nota : notaList) {
            boolean isReady = nota.getIsReady();
            if (isReady) {
                System.out.println("Laundry dengan nota ID " + nota.getIdNota() + " sudah dapat diambil!");
            }
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time");
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    // Method untuk menghitung sisa hari pengerjaan laundry
    public static int sisaHariPengerjaan(String paket, String tanggalTerima) {
        // Mengubah tanggal terima dari input menjadi LocalDate dengan menggunakan DateTimeFormatter
        String tanggalTerimaStr = tanggalTerima;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tanggalTerimaLocalDate = LocalDate.parse(tanggalTerimaStr, formatter);

        LocalDate tanggalSelesai = null;
        // Menghitung tanggal selesai berdasarkan jenis paket yang dipilih
        if (paket.equalsIgnoreCase("express")) {
            tanggalSelesai = tanggalTerimaLocalDate.plusDays(1);
        } else if (paket.equalsIgnoreCase("fast")) {
            tanggalSelesai = tanggalTerimaLocalDate.plusDays(2);
        } else if (paket.equalsIgnoreCase("reguler")) {
            tanggalSelesai = tanggalTerimaLocalDate.plusDays(3);
        } else {
            // Jika jenis paket tidak valid, program akan keluar dengan status error (0)
            System.out.println("Paket yang diinput tidak valid.");
            System.exit(0);
        }

        // Menghitung sisa hari pengerjaan dengan menggunakan ChronoUnit
        long sisaHari = ChronoUnit.DAYS.between(tanggalTerimaLocalDate, tanggalSelesai);

        return (int) sisaHari;
    }

    public static String generateId(String nama, String nomorHP) {
        Scanner input = new Scanner(System.in);

        String newNama = nama.toUpperCase().split(" ")[0];
        String newNoHP = nomorHP;

        // validasi nomor HP
        while (!newNoHP.matches("[0-9]+")) {
            System.out.println("Nomor hp hanya menerima digit.");
            System.out.print("Masukkan nomor handphone Anda: ");
            newNoHP = input.nextLine();
        }

        String idNota = newNama + "-" + newNoHP;

        // menghitung checksum
        int checksum = 0;
        for (char c : idNota.toCharArray()) {
            if (Character.isLetter(c)) {
                checksum += (int) c - 64; // karakter huruf nilainya sesuai dengan urutan pada abjad
            } else if (Character.isDigit(c)) {
                checksum += Integer.parseInt(Character.toString(c));
            } else {
                checksum += 7;
            }
        }

        // format idNota
        String checksumStr = String.format("%02d", checksum % 100);
        idNota += "-" + checksumStr;

        return idNota;
    }

    public static String generateNota(String id, String paket, int berat, String tanggalTerima) {

        // mengubah tanggal terima dari input menjadi LocalDate
        String tanggalTerimaStr = tanggalTerima;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate newtanggalTerima = LocalDate.parse(tanggalTerimaStr, formatter);

        String newPaket = paket.toLowerCase();
        int beratCucian = berat;
        if (beratCucian < 2) {
            beratCucian = 2;
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg.");
        }

        // set counter untuk diskon
        int bonusCounter = 0;
        for (Member member : memberList) {
            if (member.getId().equals(id)) {
                bonusCounter = member.getBonusCounter() + 1;
                member.setBonusCounter(bonusCounter);
                if (bonusCounter > 3) {
                    member.setBonusCounter(0);
                    bonusCounter = 0;
                }
            }
        }

        // harga dan tanggal selesai
        int harga = 0;
        LocalDate tanggalSelesai = null;
        if (paket.equalsIgnoreCase("express")) {
            harga = beratCucian * 12000;
            tanggalSelesai = newtanggalTerima.plusDays(1);
        } else if (newPaket.equalsIgnoreCase("fast")) {
            harga = beratCucian * 10000;
            tanggalSelesai = newtanggalTerima.plusDays(2);
        } else if (paket.equalsIgnoreCase("reguler")) {
            harga = beratCucian * 7000;
            tanggalSelesai = newtanggalTerima.plusDays(3);
        } else {
            System.out.println("Paket yang diinput tidak valid.");
            System.exit(0);
        }

        String status = "Belum bisa diambil :(";

        String nota = "[ ID Nota = " + idNota + " ]"
                + "\nBerhasil menambahkan nota!"
                + "\nID    : " + id + "\nPaket : " + newPaket;
        if (bonusCounter == 3) {
            nota += "\nHarga :\n" + beratCucian + " kg x " + harga / beratCucian + " = " + harga + " = " + (harga * 50) / 100 + " (Discount member 50%!!!)"
                    + "\nTanggal Terima  : " + tanggalTerima
                    + "\nTanggal Selesai : " + formatter.format(tanggalSelesai)
                    + "\nStatus : " + status;
            return nota;
        }
        nota += "\nHarga :\n" + beratCucian + " kg x " + harga / beratCucian + " = " + harga
                + "\nTanggal Terima  : " + tanggalTerima
                + "\nTanggal Selesai : " + formatter.format(tanggalSelesai)
                + "\nStatus : " + status;
        return nota;
    }

}
