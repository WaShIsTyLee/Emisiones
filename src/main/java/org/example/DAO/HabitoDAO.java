package org.example.DAO;

import org.example.Entities.Habito;
import org.example.Session.Connection;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HabitoDAO {

    public static final String findall = "FROM Habito";

    public void insertarHabito(Habito habito){
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.save(habito);
        session.getTransaction().commit();
        session.close();
    }

    public List<Habito> findAll(){
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(findall);
        List<Habito> habitos = query.getResultList();
        session.close();
        return habitos;
    }
}
