package org.example.Services;

import org.example.DAO.HabitoDAO;
import org.example.Entities.Habito;

public class HabitoService {
    HabitoDAO habitoDAO = new HabitoDAO();

    public boolean insertarHabito(Habito habito) {
        habitoDAO.insertarHabito(habito);
        return true;
    }
}
