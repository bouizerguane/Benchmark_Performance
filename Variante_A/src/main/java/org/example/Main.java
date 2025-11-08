package org.example;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    static void main() {

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        try {
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            try (Session session = sessionFactory.openSession()){
                session.beginTransaction();
                session.getTransaction().commit();
            }


        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hibernate initialisation terminée, vérifie la création des tables !");
    }
}
