package org.example.Services;

import org.example.DAO.HabitoDAO;
import org.example.Entities.Actividad;
import org.example.Entities.Habito;
import org.example.Entities.Usuario;
import org.example.Session.Session;

import java.util.ArrayList;
import java.util.List;

public class HabitoService {
    HabitoDAO habitoDAO = new HabitoDAO();

    public boolean insertarHabito(Habito habito) {
        habitoDAO.insertarHabito(habito);
        return true;
    }

    public Actividad getActividadById(Habito habito) {
        return habitoDAO.findActividadById(habito);
    }

    public List<Habito> findByUser(Usuario user) {
        List<Habito> habitos = new ArrayList<>();
        if (user != null) {
            habitos = habitoDAO.findByUser(user);
            return habitos;
        } else {
            return null;
        }

    }

    public boolean delete(Habito habito) {
        habitoDAO.delete(habito);
        return true;
    }
}
