package org.example.DAO;

import jakarta.persistence.Query;
import org.example.Entities.Actividad;
import org.example.Entities.Huella;
import org.example.Session.Connection;
import org.hibernate.Session;
import org.example.Session.Connection;
import java.util.List;

public class ActividadDAO {

    private final static String findall = "FROM Actividad";
    private final static String findActividadByName = "FROM Actividad WHERE nombre = :name";
    private final static String findUnidadByActividad = "SELECT c.unidad FROM Categoria c JOIN Actividad a ON a.idCategoria = c WHERE a.idCategoria.id = :idCategoria";


    public void insertaHuella(Huella huella) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.save(huella);
        session.close();
    }
    public List<Actividad> findAll() {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(findall);
        List<Actividad> actividades = query.getResultList();
        session.close();
        return actividades;
    }

    public Actividad findActividadByName(Actividad actividad) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(findActividadByName);
        query.setParameter("name", actividad.getNombre());
        Actividad actividadEncontrada = (Actividad) query.getSingleResult();
        return actividadEncontrada;
    }

    public String findUnidadByActividad(Actividad actividad) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(findUnidadByActividad);
        query.setParameter("idCategoria", actividad.getIdCategoria().getId());
        List<String> resultados = query.getResultList();
        String unidad = null;
        if (!resultados.isEmpty()) {
            unidad = resultados.get(0);
        }
        session.close();

        return unidad;
    }


}
