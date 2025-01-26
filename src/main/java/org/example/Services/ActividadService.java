package org.example.Services;

import org.example.DAO.ActividadDAO;
import org.example.Entities.Actividad;

import java.util.List;

public class ActividadService {
    ActividadDAO actividadDAO = new ActividadDAO();


    public List<Actividad> getAllActividades() {
        return actividadDAO.findAll();
    }

    public Actividad getActividadByName(Actividad actividad) {
        return actividadDAO.findActividadByName(actividad);
    }

    public String getUnidadByActividad(Actividad actividad) {
        return actividadDAO.findUnidadByActividad(actividad);
    }
}
