package org.example.DAO;

import org.example.Entities.Actividad;
import org.example.Entities.Habito;
import org.example.Entities.Usuario;
import org.example.Session.Connection;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HabitoDAO {

    public static final String findall = "FROM Habito";
    public static final String findByUser = "FROM Habito WHERE idUsuario.id = :id";
    public static final String findActividadName  = "FROM Actividad WHERE id = :idActividad";


    public Actividad findActividadById(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(findActividadName);
        query.setParameter("idActividad", habito.getIdActividad().getId());
        Actividad actividadEncontrada = (Actividad) query.getSingleResult();
        session.close();
        return actividadEncontrada;
    }

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
    public List<Habito> findByUser(Usuario user){
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(findByUser);
        query.setParameter("id", user.getId());
        List<Habito> habitos = query.getResultList();
        session.close();
        return habitos;
    }
}
