package org.example;

import org.example.DAO.ActividadDAO;
import org.example.DAO.UserDAO;
import org.example.Entities.Actividad;
import org.example.Entities.Usuario;
import org.example.Session.Session;

import java.time.Instant;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        Usuario usuario = new Usuario("pepe", "maria@gmail.com", "pepe", Instant.now());
        Session.getInstancia().logIn(usuario);

        List <Usuario> users = userDAO.findAll();
        for (Usuario u : users) {
            System.out.println(userDAO.calcularImpactoPorCategoriaPorIDUsuario(u) + u.getNombre());
        }
    }
}
