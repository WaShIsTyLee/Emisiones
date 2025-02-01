package org.example;

import org.example.DAO.ActividadDAO;
import org.example.DAO.UserDAO;
import org.example.Entities.Actividad;
import org.example.Entities.Usuario;

import java.time.Instant;

public class Test {
    public static void main(String[] args) {
        Usuario user = new Usuario("J", "J", "J", Instant.now());
        user.setId(4);
        UserDAO userDAO = new UserDAO();
        System.out.println(userDAO.getFactorEmision(user));
    }
}
