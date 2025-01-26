package org.example;

import org.example.DAO.ActividadDAO;
import org.example.DAO.UserDAO;
import org.example.Entities.Actividad;
import org.example.Entities.Usuario;

import java.time.Instant;

public class Test {
    public static void main(String[] args) {
        ActividadDAO actividadDAO = new ActividadDAO();
        Actividad actividad = new Actividad();
        actividad.setNombre("Consumo de agua potable");
        actividad = actividadDAO.findActividadByName(actividad);
        System.out.println(actividad);


        System.out.println(actividadDAO.findUnidadByActividad(actividad));
    }
}
