package org.example.Services;

import org.example.DAO.HuellaDAO;
import org.example.Entities.Huella;
import org.example.Entities.Usuario;

import java.math.BigDecimal;
import java.util.List;

public class HuellaServices {

    HuellaDAO huellaDAO = new HuellaDAO();

    public boolean añadirHuella(Huella huella) {
        if (huella == null || huella.getUnidad() == null || huella.getUnidad().isEmpty() ||
                huella.getValor() == null || huella.getValor().compareTo(BigDecimal.ZERO) <= 0 ||
                huella.getIdActividad() == null) {
            System.err.println("Error: Datos de huella inválidos.");
            return false;
        }

        try {
            huellaDAO.insertaHuella(huella);
            return true;
        } catch (Exception e) {
            System.err.println("Error al insertar la huella: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarHuella(Huella huella) {
        try {
            if (huella != null) {
                huellaDAO.updateHuella(huella);
                return true;
            } else {
                System.err.println("Error: Huella nula.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error actualizando la huella: " + e.getMessage());
            return false;
        }
    }

    public List<Huella> findByUserID(Usuario usuario) {
        try {
            if (usuario != null) {
                return huellaDAO.findByUserID(usuario);
            } else {
                System.err.println("Error: Usuario nulo.");
                return List.of();
            }
        } catch (Exception e) {
            System.err.println("Error al buscar huellas por usuario: " + e.getMessage());
            return List.of();
        }
    }

    public boolean delete(Huella huella) {
        try {
            if (huella != null) {
                huellaDAO.delete(huella);
                return true;
            } else {
                System.err.println("Error: Huella nula.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error eliminando la huella: " + e.getMessage());
            return false;
        }
    }
}
