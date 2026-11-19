/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kursusmobil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class KursusDAO {

    @PersistenceContext
    private EntityManager em;

    // Login: mengembalikan Pengguna jika cocok, null jika tidak
    @Transactional(readOnly = true)
    public Pengguna cekLogin(String username, String password) {
        String jpql = "SELECT p FROM Pengguna p WHERE p.username = :user AND p.password = :pass";
        TypedQuery<Pengguna> query = em.createQuery(jpql, Pengguna.class);
        query.setParameter("user", username);
        query.setParameter("pass", password);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}

