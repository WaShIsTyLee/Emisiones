package org.example;

import org.example.Entities.Recomendacion;
import org.example.Session.Connection;
import org.hibernate.Session;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Obtener la instancia única de la clase Connection
        Connection connection = Connection.getInstance();

        // Abrir una nueva sesión
        Session session = connection.getSession();

        try {
            // Iniciar la transacción
            session.beginTransaction();

            // Obtener todas las recomendaciones con sus categorías asociadas
            List<Recomendacion> recomendaciones = session.createQuery(
                    "FROM Recomendacion r JOIN FETCH r.idCategoria", Recomendacion.class).getResultList();

            // Mostrar resultados
            for (Recomendacion r : recomendaciones) {
                System.out.println("Recomendación: " + r.getDescripcion());
                System.out.println("Categoría: " + r.getIdCategoria().getNombre());
                System.out.println("Impacto estimado: " + r.getImpactoEstimado() + " kg CO2");
                System.out.println("-----------");
            }

            // Finalizar la transacción
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Revertir la transacción en caso de error
            session.getTransaction().rollback();
        } finally {
            // Cerrar la sesión
            session.close();

            // Cerrar la SessionFactory al finalizar
            connection.close();
        }
    }
}
