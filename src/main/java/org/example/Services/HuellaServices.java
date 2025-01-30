package org.example.Services;

import org.example.DAO.HuellaDAO;
import org.example.Entities.Huella;
import org.example.Entities.Usuario;

import java.math.BigDecimal;
import java.util.List;

public class HuellaServices {

    HuellaDAO huellaDAO = new HuellaDAO();

    public boolean añadirHuella(Huella huella) {
        // Validar unidad
        if (huella.getUnidad() == null || huella.getUnidad().isEmpty()) {
            System.out.println("Error: 'unidad' está vacío o es nulo.");
            return false; // Operación fallida
        }

        // Validar valor
        if (huella.getValor() == null || huella.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Error: 'valor' es nulo o no es mayor a cero.");
            return false; // Operación fallida
        }

        // Validar idActividad
        if (huella.getIdActividad() == null) {
            System.out.println("Error: 'idActividad' está vacío o es nulo.");
            return false;
        }


        try {
            huellaDAO.insertaHuella(huella);
            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar la huella en la base de datos: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarHuella(Huella huella) {
        boolean aux = false;
        try {
            huellaDAO.updateHuella(huella);
            aux = true;
        } catch (Exception e) {
            System.err.println("Error actualizando la huella: " + e.getMessage());
        }
        return aux;
    }

    public List<Huella> findByUserID(Usuario usuario) {
        return huellaDAO.findByUserID(usuario);
    }

    public boolean delete(Huella huella) {
        boolean aux = false;
        try {
            huellaDAO.delete(huella);
            aux = true;
        } catch (Exception e) {
            System.err.println("Error actualizando la huella: " + e.getMessage());
        }
        return aux;
    }


}



