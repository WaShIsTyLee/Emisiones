package org.example.Services;

import org.example.DAO.ActividadDAO;
import org.example.Entities.Actividad;
import org.example.Entities.Habito;
import org.example.Entities.Huella;

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

    public Actividad getActividadById(Huella huella) {
        return actividadDAO.findByID(huella);
    }

}
