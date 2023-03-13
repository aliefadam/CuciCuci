
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
    private static ArrayList<MemberTemp> memberList = new ArrayList<MemberTemp>();
    private static ArrayList<NotaTemp> notaList = new ArrayList<NotaTemp>();
    private static int idNota = 0;

    public static void main(String[] args) {
        String nama = "Alief";
        String nomorHp = "12345";
        String id = "ALIEF-12345-55";
        MemberTemp member = new MemberTemp(nama, nomorHp, id);
        memberList.add(member);

        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command) {
                case "1":
                    handleGenerateUser();
                    break;
                case "2":
                    handleGenerateNota();
                    break;
                case "3":
                    handleListNota();
                    break;
                case "4":
                    handleListUser();
                    break;
                case "5":
                    handleAmbilCucian();
                    break;
                case "6":
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

    private static void handleGenerateUser() {
        // TODO: handle generate user
        System.out.println("Masukkan nama Anda: ");
        String nama = input.nextLine();
        System.out.println("Masukkan nomor handphone Anda: ");
        String nomorHp = input.nextLine();

        for (MemberTemp member : memberList) {
            if (nama.equals(member.getNama()) && nomorHp.equals(member.getNoHp())) {
                System.out.println("Member dengan nama " + nama + " dan nomor hp " + nomorHp + " sudah ada!");
                return;
            }
        }

        String idNota = generateId(nama, nomorHp);

        MemberTemp member = new MemberTemp(nama, nomorHp, idNota);
        memberList.add(member);
        System.out.println("Berhasil membuat member dengan ID " + idNota + "!");

    }

    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
        System.out.println("Masukkan ID member: ");
        String idMember = input.nextLine();
        for (MemberTemp member : memberList) {
            if (idMember.equals(member.getId())) {
                String paket = "";
                do { // looping utk validasi jenis paket
                    System.out.print("Masukkan paket laundry: ");
                    paket = input.nextLine().toLowerCase();

                    if (paket.equals("?")) {
                        showPaket();  // menampilkan jenis paket jika input "?"
                    } else if (!paket.equals("express") && !paket.equals("fast") && !paket.equals("reguler")) {   // jika input paket tidak valid
                        System.out.println("Paket " + paket + " tidak diketahui.");
                        System.out.println("[Ketik ? untuk mencari tahu jenis paket.]");
                    }
                } while (!paket.equals("express") && !paket.equals("fast") && !paket.equals("reguler"));

                // validasi input berat cucian
                System.out.print("Masukkan berat cucian Anda [Kg]: ");
                String berat = input.nextLine();  // meminta input berat cucian
                while (!berat.matches("[0-9]+")) {  // input harus digit bilangan positif
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                    System.out.print("Masukkan berat cucian Anda [Kg]: ");
                    berat = input.nextLine();
                }
                int sisaHariPengerjaan = sisaHariPengerjaan(paket, fmt.format(cal.getTime()));
                String notaStruk = generateNota(idMember, paket, Integer.parseInt(berat), fmt.format(cal.getTime()));
                NotaTemp nota = new NotaTemp(member, idNota, paket, Integer.parseInt(berat), fmt.format(cal.getTime()), sisaHariPengerjaan);
                notaList.add(nota);
                System.out.println(notaStruk);
                idNota++;
                return;
            }
        }
        System.out.println("Member dengan ID " + idMember + " tidak ditemukan!");
    }
    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
        System.out.println("Terdaftar " + notaList.size() + " nota dalam sistem");
        if (!notaList.isEmpty()) {
            for (NotaTemp nota : notaList) {
                boolean isReady = nota.getIsReady();
                String status = "";
                if (isReady) {
                    status = "Sudah dapat diambil";
                } else {
                    status = "Belum bisa diambil :(";
                }
                System.out.println("- [" + nota.getIdNota() + "] Status : " + status);
            }
        }
    }

    private static void handleListUser() {
        // TODO: handle list semua user pada sistem
        System.out.println("Terdaftar " + memberList.size() + " member dalam sistem.");

        if (!memberList.isEmpty()) {
            for (MemberTemp member : memberList) {
                System.out.println("- " + member.getId() + " : " + member.getNama());
            }
        }
    }

    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
        System.out.println("Masukkan ID nota yang akan diambil: ");
        String idNota = input.nextLine();
        while (!idNota.matches("[0-9]+")) {
            System.out.println("ID nota berbentuk angka!");
            System.out.println("Masukkan ID nota yang akan diambil: ");
            idNota = input.nextLine();
        }
        for (int i = 0; i < notaList.size(); i++) {
            if (notaList.get(i).getIdNota() == Integer.parseInt(idNota)) {
                if (notaList.get(i).getSisaHariPengerjaan() == 0) {
                    notaList.remove(i);
                    System.out.println("Nota dengan ID " + idNota + " berhasil diambil!");
                    return;
                } else {
                    System.out.println("sisah hari pengerjaan : " + notaList.get(i).getSisaHariPengerjaan());
                    System.out.println("Nota dengan ID " + idNota + " gagal diambil!");
                    return;
                }
            }
        }
        System.out.println("Nota dengan ID " + idNota + " tidak ditemukan!");

    }

    private static void handleNextDay() {
        // TODO: handle ganti hari
        cal.add(Calendar.DAY_OF_MONTH, 1);
        for (NotaTemp nota : notaList) {
            nota.setSisaHariPengerjaan(1);
        }
        System.out.println("Dek Depe tidur hari ini... zzz...");
        for (NotaTemp nota : notaList) {
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

    public static int sisaHariPengerjaan(String paket, String tanggalTerima) {
        // mengubah tanggal terima dari input menjadi LocalDate
        String tanggalTerimaStr = tanggalTerima;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tanggalTerimaLocalDate = LocalDate.parse(tanggalTerimaStr, formatter);

        LocalDate tanggalSelesai = null;
        if (paket.equalsIgnoreCase("express")) {
            tanggalSelesai = tanggalTerimaLocalDate.plusDays(1);
        } else if (paket.equalsIgnoreCase("fast")) {
            tanggalSelesai = tanggalTerimaLocalDate.plusDays(2);
        } else if (paket.equalsIgnoreCase("reguler")) {
            tanggalSelesai = tanggalTerimaLocalDate.plusDays(3);
        } else {
            System.out.println("Paket yang diinput tidak valid.");
            System.exit(0);
        }

        long sisaHari = ChronoUnit.DAYS.between(tanggalTerimaLocalDate, tanggalSelesai);

        return (int) sisaHari;
    }

    public static String generateId(String nama, String nomorHP) {
        // input
        Scanner input = new Scanner(System.in);
        String newNama = nama.toUpperCase().split(" ")[0];
        String newNoHP = nomorHP;

        // validasi nomor HP
        while (!newNoHP.matches("[0-9]+")) {
            System.out.println("Nomor hp hanya menerima digit.");
            System.out.print("Masukkan nomor handphone Anda: ");
            newNoHP = input.nextLine();
        }
        // menggabungkan nama dan nomor HP
        String idNota = newNama + "-" + newNoHP;

        // menghitung checksum
        int checksum = 0;
        for (char c : idNota.toCharArray()) {
            if (Character.isLetter(c)) {
                checksum += (int) c - 64;   // karakter huruf nilainya sesuai dengan urutan pada abjad
            } else if (Character.isDigit(c)) {
                checksum += Integer.parseInt(Character.toString(c));
            } else {
                checksum += 7;
            }
        }

        // jika checksum hanya 1 digit
        String checksumStr = String.valueOf(checksum);
        if (checksumStr.length() == 1) {
            checksumStr = "0" + checksumStr;    // menambahkan leading 0
        }
        idNota += "-" + checksumStr;      // menambahkan checksum ke idNota

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
        for (MemberTemp member : memberList) {
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
