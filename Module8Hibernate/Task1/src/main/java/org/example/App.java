package org.example;

import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Configuration cfg = new Configuration().addAnnotatedClass(Person.class);
        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();

            Person person = new Person("John", 22);
            session.save(person);

            //session.createQuery("update Person set name='Test' where name LIKE 'J%'").executeUpdate();

            session.createQuery("delete from Person where name LIKE 'Test%'").executeUpdate();
            List<Person> people = session.createQuery("from Person").getResultList();
            for (Person p : people) {
                System.out.println(p);
            }
            session.getTransaction().commit();
        } finally {
            sf.close();
        }
    }
}
