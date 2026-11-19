/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kursusmobil;

import java.sql.Connection;
import java.sql.DriverManager;

public class koneksi {

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/kursus", "root", "");
    }

    // Untuk tes: klik kanan file ini → Run File
    public static void main(String[] args) {
        try (Connection c = getConnection()) {
            System.out.println("Koneksi berhasil!");
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
    }
}
