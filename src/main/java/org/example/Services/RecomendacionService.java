package org.example.Services;

import org.example.DAO.RecomendacionDAO;
import org.example.Entities.Habito;
import org.example.Entities.Recomendacion;

import java.util.List;

public class RecomendacionService {
    RecomendacionDAO recomendacionDAO = new RecomendacionDAO();

    public List<Recomendacion> findRecomendacionesForUser(List <Habito> habitos, List<Recomendacion> recomendaciones) {
        if (habitos == null) {
            return null;
        } else {
            for (Habito habito : habitos) {
                recomendaciones.addAll(recomendacionDAO.findRecomendacionesForUser(habito));
            }
            return recomendaciones;
        }

    }
}
