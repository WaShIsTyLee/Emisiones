package org.example.DAO;

import org.example.Entities.Habito;
import org.example.Entities.Recomendacion;
import org.example.Session.Connection;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RecomendacionDAO{

    private static final String findRecomendacionesForUser = "SELECT r FROM Recomendacion r WHERE r.idCategoria.id = :idCategoria";


    public List <Recomendacion> findRecomendacionesForUser(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(findRecomendacionesForUser);
        query.setParameter("idCategoria", habito.getIdActividad().getIdCategoria().getId());
        List<Recomendacion> recomendaciones = query.getResultList();
        session.close();
        return recomendaciones;

    }
}
