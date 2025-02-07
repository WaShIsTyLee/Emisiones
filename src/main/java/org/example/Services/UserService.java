package org.example.Services;

import org.example.DAO.UserDAO;
import org.example.Entities.Usuario;
import org.example.Session.Session;
import org.example.Utils.Utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean logUser(Usuario user) {
        try {
            if (user == null || user.getEmail() == null || user.getContraseña() == null ||
                    user.getEmail().isEmpty() || user.getContraseña().isEmpty()) {
                System.err.println("Error: Email o contraseña no pueden estar vacíos.");
                return false;
            }

            user.setContraseña(Utils.hashPassword(user.getContraseña()));
            Usuario usuarioEncontrado = userDAO.findUserByEmail(user);

            if (usuarioEncontrado != null &&
                    Utils.hashPassword(user.getContraseña()).equals(Utils.hashPassword(usuarioEncontrado.getContraseña()))) {
                Session.getInstancia().logIn(usuarioEncontrado);
                System.out.println("Inicio de sesión exitoso");
                return true;
            } else {
                System.out.println("Credenciales incorrectas");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error en el inicio de sesión: " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> traerUsuarios() {
        try {
            return userDAO.findAll();
        } catch (Exception e) {
            System.err.println("Error al traer usuarios: " + e.getMessage());
            return List.of();
        }
    }

    public boolean registerUser(Usuario user) {
        try {
            if (user == null || user.getEmail() == null || user.getContraseña() == null ||
                    user.getEmail().isEmpty() || user.getContraseña().isEmpty() ||
                    !Utils.esCorreoGmailValido(user.getEmail())) {
                System.err.println("Error: Datos de usuario inválidos.");
                return false;
            }

            if (userDAO.findUserByEmail(user) != null) {
                System.out.println("El email ya está registrado.");
                return false;
            }

            user.setContraseña(Utils.hashPassword(user.getContraseña()));
            userDAO.insertUsuario(user);
            Session.getInstancia().logIn(user);
            return true;
        } catch (Exception e) {
            System.err.println("Error en el registro de usuario: " + e.getMessage());
            return false;
        }
    }

    public List<BigDecimal> getFactorEmision(Usuario user) {
        try {
            if (user != null) {
                return userDAO.getFactorEmision(user);
            } else {
                System.err.println("Error: Usuario nulo.");
                return List.of();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener el factor de emisión: " + e.getMessage());
            return List.of();
        }
    }

    public boolean actualizarUsuario(Usuario user) {
        try {
            if (user != null) {
                userDAO.updateUsuario(user);
                return true;
            } else {
                System.err.println("Error: Usuario nulo.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar el usuario: " + e.getMessage());
            return false;
        }
    }

    public Map<String, BigDecimal> getFactorEmisionMap(Usuario user) {
        try {
            if (user != null) {
                return userDAO.calcularImpactoPorCategoriaPorIDUsuario(user);
            } else {
                System.err.println("Error: Usuario nulo.");
                return Map.of();
            }
        } catch (Exception e) {
            System.err.println("Error al calcular impacto por categoría: " + e.getMessage());
            return Map.of();
        }
    }
}
