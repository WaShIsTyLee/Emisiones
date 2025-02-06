package org.example.Services;

import org.example.DAO.UserDAO;
import org.example.Entities.Usuario;
import org.example.Session.Session;
import org.example.Utils.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public boolean logUser(Usuario user) {
        user.setContraseña(Utils.hashPassword(user.getContraseña()));
        if (user.getEmail() == null || user.getContraseña() == null || user.getEmail().isEmpty() || user.getContraseña().isEmpty()) {
            return false;
        } else {
            Usuario usuarioEncontrado = userDAO.findUserByEmail(user);
            if (usuarioEncontrado != null && Utils.hashPassword(user.getContraseña()).equals(Utils.hashPassword(usuarioEncontrado.getContraseña()))) {
                Session.getInstancia().logIn(usuarioEncontrado);
                System.out.println("Inicio de sesión exitoso");
                return true;
            } else {
                System.out.println("Credenciales incorrectas");
                return false;
            }

        }
    }




    public List<Usuario> trearUsuarios() {
        return userDAO.findAll();
    }

    public boolean registerUser(Usuario user) {
        if (!Utils.esCorreoGmailValido(user.getEmail()) || user.getEmail() == null || user.getContraseña() == null || user.getEmail().isEmpty() || user.getContraseña().isEmpty()) {
            return false;
        } else {
            Usuario usuarioExistente = userDAO.findUserByEmail(user);
            if (usuarioExistente != null) {
                System.out.println("El email ya está registrado.");
                return false;
            } else {
                user.setContraseña(Utils.hashPassword(user.getContraseña()));
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
