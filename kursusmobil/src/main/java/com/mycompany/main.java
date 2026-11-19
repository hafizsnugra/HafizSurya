/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kursusmobil;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Ramelan
 */
public class main {
    
    public static void main(String[] args) {
        // 1. Jalankan Spring Container & ambil bean DAO
        AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(AppConfig.class);
        KursusDAO kursusDAO = context.getBean(KursusDAO.class);

        // 2. Tampilkan form login dengan menyuntikkan kursusDAO
        javax.swing.SwingUtilities.invokeLater(() -> {
            javax.swing.JFrame frame = new javax.swing.JFrame("Login");
            
            frame.setDefaultCloseOperation(
                javax.swing.JFrame.EXIT_ON_CLOSE
            );

            frame.setContentPane(new loginform(kursusDAO));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
