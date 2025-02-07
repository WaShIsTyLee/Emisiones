package org.example.Services;

import org.example.DAO.HabitoDAO;
import org.example.Entities.Actividad;
import org.example.Entities.Habito;
import org.example.Entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class HabitoService {
    HabitoDAO habitoDAO = new HabitoDAO();

    public boolean insertarHabito(Habito habito) {
        try {
            if (habito != null) {
                habitoDAO.insertarHabito(habito);
                return true;
            } else {
                System.err.println("Error: El hábito no puede ser nulo.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al insertar el hábito: " + e.getMessage());
            return false;
        }
    }

    public Actividad getActividadById(Habito habito) {
        try {
            if (habito != null) {
                return habitoDAO.findActividadById(habito);
            } else {
                System.err.println("Error: El hábito no puede ser nulo.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al obtener actividad por ID: " + e.getMessage());
            return null;
        }
    }

    public List<Habito> findByUser(Usuario user) {
        try {
            if (user != null) {
                return habitoDAO.findByUser(user);
            } else {
                System.err.println("Error: El usuario no puede ser nulo.");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("Error al buscar hábitos por usuario: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean delete(Habito habito) {
        try {
            if (habito != null) {
                habitoDAO.delete(habito);
                return true;
            } else {
                System.err.println("Error: El hábito no puede ser nulo.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar el hábito: " + e.getMessage());
            return false;
        }
    }
}
