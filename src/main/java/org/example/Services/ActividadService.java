package org.example.Services;

import org.example.DAO.ActividadDAO;
import org.example.Entities.Actividad;
import org.example.Entities.Huella;
import java.util.List;

public class ActividadService {
    private final ActividadDAO actividadDAO = new ActividadDAO();

    public List<Actividad> getAllActividades() {
        try {
            return actividadDAO.findAll();
        } catch (Exception e) {
            System.err.println("Error al obtener todas las actividades: " + e.getMessage());
            return null;
        }
    }

    public Actividad getActividadByName(Actividad actividad) {
        try {
            return actividadDAO.findActividadByName(actividad);
        } catch (Exception e) {
            System.err.println("Error al obtener la actividad por nombre: " + e.getMessage());
            return null;
        }
    }

    public String getUnidadByActividad(Actividad actividad) {
        try {
            return actividadDAO.findUnidadByActividad(actividad);
        } catch (Exception e) {
            System.err.println("Error al obtener la unidad de la actividad: " + e.getMessage());
            return null;
        }
    }

    public Actividad getActividadById(Huella huella) {
        try {
            return actividadDAO.findByID(huella);
        } catch (Exception e) {
            System.err.println("Error al obtener la actividad por ID: " + e.getMessage());
            return null;
        }
    }
}
