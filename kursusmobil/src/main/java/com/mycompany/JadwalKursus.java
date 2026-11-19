/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kursusmobil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 * Jendela Jadwal Kursus: tampilan + data + database dalam satu file.
 *
 * @author lenovo
 */
public class JadwalKursus extends JFrame {

    // Pengaturan database (sesuaikan kalau username/password MySQL kamu berbeda)
    private static final String URL = "jdbc:mysql://localhost:3306/kursus";
    private static final String USER = "root";
    private static final String PASS = "";

    // Atribut
    private int idJadwal;
    private String tanggal;
    private String hari;
    private String jam;
    private String namaPeserta;
    private String namaInstruktur;
    private String jenisMobil;

    // Komponen tampilan
    private final JTextField tfTanggal = new JTextField();
    private final JTextField tfJam = new JTextField();
    private final JTextField tfPeserta = new JTextField();
    private final JTextField tfInstruktur = new JTextField();
    private final JTextField tfHari = new JTextField();
    private final JRadioButton rbManual = new JRadioButton("Manual", true);
    private final JRadioButton rbMatic = new JRadioButton("Matic");
    private final JLabel lbJumlah = new JLabel(" ");

    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Tanggal", "Hari", "Jam", "Peserta", "Instruktur", "Mobil"}, 0) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    private final JTable tabel = new JTable(model);

    // Constructor: membangun jendela
    public JadwalKursus() {
        setTitle("Jadwal Kursus Mobil");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        add(buatHeader(), BorderLayout.NORTH);

        JPanel isi = panel(new BorderLayout(0, 12));
        isi.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));
        isi.add(buatForm(), BorderLayout.NORTH);
        isi.add(buatTabel(), BorderLayout.CENTER);
        add(isi, BorderLayout.CENTER);

        JPanel bawah = panel(new FlowLayout(FlowLayout.LEFT));
        bawah.setBorder(BorderFactory.createEmptyBorder(0, 12, 4, 12));
        bawah.add(lbJumlah);
        add(bawah, BorderLayout.SOUTH);

        // Hari terisi otomatis dari tanggal
        tfHari.setEditable(false);
        tfHari.setBackground(Color.WHITE);
        tfHari.setText("otomatis");
        tfTanggal.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { perbaruiHari(); }
            public void removeUpdate(DocumentEvent e) { perbaruiHari(); }
            public void changedUpdate(DocumentEvent e) { perbaruiHari(); }
        });

        SwingUtilities.invokeLater(this::muat);
    }

    // ---------- tampilan (hitam putih) ----------

    private JPanel panel(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(Color.WHITE);
        return p;
    }

    private JButton tombol(String teks, boolean utama) {
        JButton b = new JButton(teks);
        b.setOpaque(true);
        b.setFocusPainted(false);
        b.setBackground(utama ? Color.BLACK : Color.WHITE);
        b.setForeground(utama ? Color.WHITE : Color.BLACK);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(5, 16, 5, 16)));
        return b;
    }

    private JPanel buatHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(Color.BLACK);
        h.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        JLabel judul = new JLabel("JADWAL KURSUS MOBIL");
        judul.setForeground(Color.WHITE);
        judul.setFont(judul.getFont().deriveFont(Font.BOLD, 18f));
        h.add(judul, BorderLayout.WEST);
        return h;
    }

    private JPanel buatForm() {
        JPanel kotak = panel(new BorderLayout(0, 10));

        // Isian: dua kolom
        JPanel f = panel(new GridBagLayout());
        f.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Data Jadwal"));

        ButtonGroup grup = new ButtonGroup();
        grup.add(rbManual);
        grup.add(rbMatic);
        rbManual.setBackground(Color.WHITE);
        rbMatic.setBackground(Color.WHITE);
        JPanel mobil = panel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        mobil.add(rbManual);
        mobil.add(rbMatic);

        taruh(f, label("Tanggal (yyyy-MM-dd)"), 0, 0, 0);
        taruh(f, tfTanggal, 1, 0, 1);
        taruh(f, label("Jam (HH:mm)"), 2, 0, 0);
        taruh(f, tfJam, 3, 0, 1);
        taruh(f, label("Nama peserta"), 0, 1, 0);
        taruh(f, tfPeserta, 1, 1, 1);
        taruh(f, label("Nama instruktur"), 2, 1, 0);
        taruh(f, tfInstruktur, 3, 1, 1);
        taruh(f, label("Jenis mobil"), 0, 2, 0);
        taruh(f, mobil, 1, 2, 1);
        taruh(f, label("Hari"), 2, 2, 0);
        taruh(f, tfHari, 3, 2, 1);

        // Tombol
        JButton btnSimpan = tombol("Simpan", true);
        JButton btnHapus = tombol("Hapus", false);
        JButton btnReset = tombol("Reset", false);
        JButton btnLogout = tombol("Logout", false);
        btnSimpan.addActionListener(e -> simpan());
        btnHapus.addActionListener(e -> hapus());
        btnReset.addActionListener(e -> reset());
        btnLogout.addActionListener(e -> logout());

        JPanel kiri = panel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        kiri.add(btnSimpan);
        kiri.add(btnHapus);
        kiri.add(btnReset);
        JPanel kanan = panel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        kanan.add(btnLogout);
        JPanel baris = panel(new BorderLayout());
        baris.add(kiri, BorderLayout.WEST);
        baris.add(kanan, BorderLayout.EAST);

        kotak.add(f, BorderLayout.CENTER);
        kotak.add(baris, BorderLayout.SOUTH);
        return kotak;
    }

    private void taruh(JPanel p, Component c, int x, int y, double lebar) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = x;
        g.gridy = y;
        g.weightx = lebar;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        g.insets = new Insets(6, 10, 6, 10);
        p.add(c, g);
    }

    private JLabel label(String teks) {
        JLabel l = new JLabel(teks);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        return l;
    }

    private JScrollPane buatTabel() {
        tabel.setRowHeight(26);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabel.setGridColor(Color.BLACK);
        tabel.setBackground(Color.WHITE);
        tabel.setSelectionBackground(Color.BLACK);
        tabel.setSelectionForeground(Color.WHITE);
        tabel.getTableHeader().setBackground(Color.WHITE);
        tabel.getTableHeader().setForeground(Color.BLACK);
        tabel.getTableHeader().setFont(tabel.getTableHeader().getFont().deriveFont(Font.BOLD));
        tabel.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabel.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabel.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabel.getColumnModel().getColumn(3).setPreferredWidth(60);
        tabel.getColumnModel().getColumn(6).setPreferredWidth(70);
        JScrollPane sp = new JScrollPane(tabel);
        sp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sp.getViewport().setBackground(Color.WHITE);
        return sp;
    }

    // ---------- database & aksi ----------

    private static Connection koneksi() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private void perbaruiHari() {
        try {
            tfHari.setText(namaHari(LocalDate.parse(tfTanggal.getText().trim())));
        } catch (DateTimeParseException e) {
            tfHari.setText("otomatis");
        }
    }

    private static String namaHari(LocalDate d) {
        String[] n = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
        return n[d.getDayOfWeek().getValue() - 1];
    }

    // Ambil data dari database ke tabel
    private void muat() {
        model.setRowCount(0);
        String sql = "SELECT * FROM jadwal_kursus ORDER BY tanggal, jam";
        try (Connection c = koneksi();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_jadwal"),
                    rs.getString("tanggal"),
                    rs.getString("hari"),
                    rs.getString("jam").substring(0, 5),
                    rs.getString("nama_peserta"),
                    rs.getString("nama_instruktur"),
                    rs.getString("jenis_mobil")});
            }
            lbJumlah.setText("Total: " + model.getRowCount() + " jadwal");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void simpan() {
        String tgl = tfTanggal.getText().trim();
        String jamIsi = tfJam.getText().trim();
        String peserta = tfPeserta.getText().trim();
        String instruktur = tfInstruktur.getText().trim();
        if (tgl.isEmpty() || jamIsi.isEmpty() || peserta.isEmpty() || instruktur.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lengkapi semua data dulu.");
            return;
        }
        LocalDate d;
        LocalTime t;
        try {
            d = LocalDate.parse(tgl);
            t = LocalTime.parse(jamIsi);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal: 2026-11-16, format jam: 08:00");
            return;
        }

        // Isi atribut dari form
        setTanggal(d.toString());
        setHari(namaHari(d));
        setJam(String.format("%02d:%02d", t.getHour(), t.getMinute()));
        setNamaPeserta(peserta);
        setNamaInstruktur(instruktur);
        setJenisMobil(rbMatic.isSelected() ? "Matic" : "Manual");

        String sql = "INSERT INTO jadwal_kursus "
                + "(tanggal, hari, jam, nama_peserta, nama_instruktur, jenis_mobil) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = koneksi(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(getTanggal()));
            ps.setString(2, getHari());
            ps.setTime(3, java.sql.Time.valueOf(getJam() + ":00"));
            ps.setString(4, getNamaPeserta());
            ps.setString(5, getNamaInstruktur());
            ps.setString(6, getJenisMobil());
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
            return;
        }
        reset();
        muat();
    }

    private void hapus() {
        int r = tabel.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Pilih dulu satu baris di tabel.");
            return;
        }
        int id = (int) model.getValueAt(r, 0);
        if (JOptionPane.showConfirmDialog(this, "Hapus jadwal ID " + id + "?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        try (Connection c = koneksi();
             PreparedStatement ps = c.prepareStatement("DELETE FROM jadwal_kursus WHERE id_jadwal = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus: " + e.getMessage());
            return;
        }
        muat();
    }

    private void reset() {
        tfTanggal.setText("");
        tfJam.setText("");
        tfPeserta.setText("");
        tfInstruktur.setText("");
        rbManual.setSelected(true);
        tabel.clearSelection();
    }

    private void logout() {
        this.dispose();
        new loginform().setVisible(true);
    }

    // Untuk menjalankan langsung: klik kanan file ini -> Run File
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JadwalKursus().setVisible(true));
    }

    // Getter dan Setter
    public int getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(int idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getNamaPeserta() {
        return namaPeserta;
    }

    public void setNamaPeserta(String namaPeserta) {
        this.namaPeserta = namaPeserta;
    }

    public String getNamaInstruktur() {
        return namaInstruktur;
    }

    public void setNamaInstruktur(String namaInstruktur) {
        this.namaInstruktur = namaInstruktur;
    }

    public String getJenisMobil() {
        return jenisMobil;
    }

    public void setJenisMobil(String jenisMobil) {
        this.jenisMobil = jenisMobil;
    }
}