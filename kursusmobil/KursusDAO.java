/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kursusmobil;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class KursusDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Method Login (Pengganti query SQL di loginform.java)
    public Pengguna cekLogin(String username, String password) {
        String hql = "FROM Pengguna WHERE username = :user AND password = :pass";
        Query<Pengguna> query = sessionFactory.getCurrentSession().createQuery(hql, Pengguna.class);
        query.setParameter("user", username);
        query.setParameter("pass", password);
        return query.uniqueResult();
    }
}

