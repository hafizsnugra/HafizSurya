package com.mycompany.kursusmobil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class CetakLaporan {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/kursus";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void cetakJadwalKursus() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            // Mencari file jadwalkursus.jrxml di folder reports (baik di resources maupun di dalam package)
            InputStream reportStream = CetakLaporan.class.getResourceAsStream("/reports/jadwalkursus.jrxml");
            if (reportStream == null) {
                reportStream = CetakLaporan.class.getResourceAsStream("/com/mycompany/kursusmobil/reports/jadwalkursus.jrxml");
            }
            if (reportStream == null) {
                throw new RuntimeException("File jadwalkursus.jrxml tidak ditemukan di folder reports!");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);

            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mencetak laporan: " + e.getMessage(), "Error Laporan", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}