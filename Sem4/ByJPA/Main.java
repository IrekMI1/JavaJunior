package Sem4.ByJPA;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connector connector = new Connector();
        Session session = connector.getSession();
        session.beginTransaction();
        session.save(new Book("Hopes", "Greek Mitanse"));
        session.save(new Book("Life is Long", "Greek Mitanse"));
        session.save(new Book("Kids in adventure", "Alan Bury"));
        session.save(new Book("Lean On Me", "Sophia Nellis"));
        session.save(new Book("Day in Miami", "Alan Bury"));
        session.save(new Book("Summer Rain", "Henry Jason"));
        session.save(new Book("Closeup", "Mike Petersen"));
        session.save(new Book("Rides on bike", "Henry Jason"));
        session.save(new Book("Flowers in the river", "Kelly Monsoon"));
        session.save(new Book("Life on Earth", "Lisa Mann"));
        session.getTransaction().commit();
        session.close();

        try (Session session1 = connector.getSession()) {
            List<Book> books = session1.createQuery(
                    "FROM Books where author='Greek Mitanse'", Book.class).getResultList();
            books.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
