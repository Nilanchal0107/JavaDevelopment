package org.nilanchal;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {

        Alien a1 = new Alien();
        a1.setAid(104);
        a1.setUname("Binayak");
        a1.setTech("AIML");

        // Hey hibernate save with a1 object

        Configuration config = new Configuration();
        config.addAnnotatedClass(org.nilanchal.Alien.class);
        config.configure();

        SessionFactory factory = new Configuration()
                .addAnnotatedClass(org.nilanchal.Alien.class)
                .configure()
                .buildSessionFactory();

        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();

//        Alien a1 = session.get(Alien.class, 102);   Eager Fetching

//        Alien a1 = session.byId(Alien.class).getReference(103);  Lazy Fetching

//        System.out.println(a1);

//        session.merge(a1);
//
//        Alien a1 = session.find(Alien.class, 104);
//
//        session.remove(a1);

        session.persist(a1);

        transaction.commit();

        session.close();
        factory.close();
    }
}