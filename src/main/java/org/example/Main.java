package org.example;

import org.example.Entities.Recomendacion;
import org.example.Session.Connection;
import org.hibernate.Session;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();

        try {
            session.beginTransaction();
            List<Recomendacion> recomendaciones = session.createQuery(
                    "FROM Recomendacion r JOIN FETCH r.idCategoria", Recomendacion.class).getResultList();

            for (Recomendacion r : recomendaciones) {
                System.out.println("Recomendación: " + r.getDescripcion());
                System.out.println("Categoría: " + r.getIdCategoria().getNombre());
                System.out.println("Impacto estimado: " + r.getImpactoEstimado() + " kg CO2");
                System.out.println("-----------");
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            connection.close();
        }
    }
}
