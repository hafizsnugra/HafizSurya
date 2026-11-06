/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kursusmobil;

/**
 *
 * @author lenovo
 */
public class JadwalKursus {

    // Atribut
    private int idJadwal;
    private String tanggal;
    private String hari;
    private String jam;
    private String namaPeserta;
    private String namaInstruktur;
    private String jenisMobil;

    // Constructor
    public JadwalKursus(int idJadwal, String tanggal, String hari,
                        String jam, String namaPeserta,
                        String namaInstruktur, String jenisMobil) {
        this.idJadwal = idJadwal;
        this.tanggal = tanggal;
        this.hari = hari;
        this.jam = jam;
        this.namaPeserta = namaPeserta;
        this.namaInstruktur = namaInstruktur;
        this.jenisMobil = jenisMobil;
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
