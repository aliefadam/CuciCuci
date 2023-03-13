
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);

        ArrayList<Member> members = new ArrayList<Member>();
        ArrayList<Nota> notas = new ArrayList<Nota>();

        int pilihan;
        String nama = "", nomorHp = "", idMember = "", paket = "", berat = "";

        do {
            printMenu(formattedDate);
            System.out.print("Pilihan : ");
            pilihan = input.nextInt();
            System.out.println("================================");
            input.nextLine();
            switch (pilihan) {
                case 1:
                    boolean isExistMember = false;
                    System.out.print("Masukkan nama anda: ");
                    nama = input.nextLine();
                    System.out.print("Masukkan nomor handphone anda: ");
                    nomorHp = input.nextLine();
                    String id = generateId(nama, nomorHp);

                    for (Member listMember : members) {
                        if (listMember.getNama().equals(nama) && listMember.getNoHp().equals(nomorHp)) {
                            System.out.println("Member dengan nama " + listMember.getNama() + " dan nomor hp " + listMember.getNoHp() + " sudah ada!");
                            isExistMember = true;
                        }
                    }
                    if (!isExistMember) {
                        Member member = new Member(nama, nomorHp, id);
                        members.add(member);
                        System.out.println("Berhasil membuat member dengan ID " + id);
                    }

                    break;
                case 2:
                    System.out.print("Masukkan ID member: ");
                    idMember = input.nextLine();
                    boolean isExistNota = false;
                    for (Member listMember : members) {
                        if (listMember.getId().equals(idMember)) {
                            do {
                                System.out.print("Masukkan paket laundry: ");
                                paket = input.nextLine().toLowerCase();

                                if (paket.equals("?")) {
                                    showPaket();
                                } else if (!paket.equals("express") && !paket.equals("fast") && !paket.equals("reguler")) {   // jika input paket tidak valid
                                    System.out.println("Paket " + paket + "tidak diketahui.");
                                    System.out.println("[Ketik ? untuk mencari tahu jenis paket.]");
                                }
                            } while (!paket.equals("express") && !paket.equals("fast") && !paket.equals("reguler"));

                            System.out.print("Masukkan berat cucian Anda [Kg]: ");
                            berat = input.nextLine();
                            while (!berat.matches("[0-9]+")) {
                                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                                System.out.print("Masukkan berat cucian Anda [Kg]: ");
                                berat = input.nextLine();
                            }
                            int i = 0;
                            int sisaHari = sisaHariPengerjaan(paket, formattedDate);
                            String status = "";
                            boolean isReady = false;
                            if (sisaHari == 0) {
                                isReady = true;
                                status = "Sudah dapat diambil";
                            } else {
                                status = "Belum bisa diambil :(";
                            }
                            idMember = generateId(nama, nomorHp);
                            String cetakNota = generateNota(idMember, paket, Integer.parseInt(berat), formattedDate, members, status);

                            Nota nota = new Nota(i++, paket, Integer.parseInt(berat), formattedDate, sisaHari, isReady, listMember, status);
                            notas.add(nota);
                            System.out.println(cetakNota);
                            isExistNota = true;
                        }
                    }
                    if (!isExistNota) {
                        System.out.println("Member dengan ID " + idMember + " tidak ditemukan");
                    }
                    break;
                case 3:
                    for (Nota listNota : notas) {
                        System.out.println("- [" + listNota.getIdNota() + "] Status: " + listNota.getStatus());
                    }
                    break;
                case 4:
                    System.out.println("Terdaftar " + members.size() + " member dalam sistem");
                    for (Member listMember : members) {
                        System.out.println("- " + listMember.getId() + ": " + listMember.getNama());
                    }
                    break;
                case 6:
                    System.out.println("Dek Depe tidur hari ini... zzz...");
                    System.out.println("Selamat pagi dunia!");
                    System.out.println("Dek Depe: It's CuciCuci Time.");
                    today = today.plusDays(1);
                    formattedDate = today.format(formatter);
                    System.out.println(formattedDate);
                    break;
                case 0:
                    System.out.println("Terima kasih telah menggunakan NotaGenerator!");
                    break;
                default:
                    System.out.println("Perintah tidak diketahui, silahkan periksa kembali");
                    break;
            }
            System.out.println("");
        } while (pilihan != 0);

    }

    /**
     * Method untuk menampilkan menu di NotaGenerator.
     */
    private static void printMenu(String formattedDate) {
        System.out.println("Selamat datang di CuciCuci!");
        System.out.println("Sekarang Tanggal: " + formattedDate);
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List Member");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

    /**
     * Method untuk menampilkan paket.
     */
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk membuat ID dari nama dan nomor handphone. Parameter dan
     * return type dari method ini tidak boleh diganti agar tidak mengganggu
     * testing
     *
     * @return String ID anggota dengan format
     * [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHp) {
        // input
        Scanner input = new Scanner(System.in);
        String newNama = nama.toUpperCase().split(" ")[0];
        String newNoHP = nomorHp;

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

    /**
     *
     * Method untuk membuat Nota. Parameter dan return type dari method ini
     * tidak boleh diganti agar tidak mengganggu testing.
     *
     * @return string nota dengan format di bawah:
     * <p>
     * ID : [id]
     * <p>
     * Paket : [paket]
     * <p>
     * Harga :
     * <p>
     * [berat] kg x [hargaPaketPerKg] = [totalHarga]
     * <p>
     * Tanggal Terima : [tanggalTerima]
     * <p>
     * Tanggal Selesai : [tanggalTerima + LamaHariPaket]
     */
    public static String generateNota(String id, String paket, int berat, String tanggalTerima, ArrayList<Member> members, String status) {
        Scanner input = new Scanner(System.in);

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

        String nota = "[ ID Nota = " + (members.size() - 1) + " ]"
                + "\nID    : " + id + "\nPaket : " + newPaket
                + "\nHarga :\n" + beratCucian + " kg x " + harga / beratCucian
                + " = " + harga + "\nTanggal Terima  : " + tanggalTerima
                + "\nTanggal Selesai : " + formatter.format(tanggalSelesai)
                + "\nStatus : " + status;
        return nota;
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

}
