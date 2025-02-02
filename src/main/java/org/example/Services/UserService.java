package org.example.Services;

import org.example.DAO.UserDAO;
import org.example.Entities.Usuario;
import org.example.Session.Session;

import java.math.BigDecimal;
import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public boolean logUser(Usuario user) {
        if (user.getEmail() == null || user.getContraseña() == null || user.getEmail().isEmpty() || user.getContraseña().isEmpty()) {
            return false;
        } else {
            Usuario usuarioEncontrado = userDAO.findUserByEmail(user);
            if (usuarioEncontrado != null && user.getContraseña().equals(usuarioEncontrado.getContraseña())) {
                Session.getInstancia().logIn(usuarioEncontrado);
                System.out.println(usuarioEncontrado);
                return true;
            } else {
                System.out.println("CONTRASEÑA MALA");
                return false;
            }

        }
    }

    public boolean registerUser(Usuario user) {
        if (user.getEmail() == null || user.getContraseña() == null || user.getEmail().isEmpty() || user.getContraseña().isEmpty()) {
            return false;
        } else {
            Usuario usuarioExistente = userDAO.findUserByEmail(user);
            if (usuarioExistente != null) {
                System.out.println("El email ya está registrado.");
                return false;
            } else {
                userDAO.insertUsuario(user);
                Session.getInstancia().logIn(user);
                return true;
            }
        }
    }

    public List<BigDecimal> getFactorEmision(Usuario user) {
        return userDAO.getFactorEmision(user);
    }

    public boolean actualizarUsuario(Usuario user) {
        userDAO.updateUsuario(user);
        return true;
    }
}
