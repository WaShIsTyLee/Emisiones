package org.example.DAO;

import jakarta.persistence.Query;
import org.example.Entities.Categoria;
import org.example.Entities.Usuario;
import org.example.Session.Connection;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {

    // 📌 Consultas definidas como constantes
    private final static String FIND_USER_BY_ID = "FROM Usuario WHERE id = :id";
    private final static String FIND_USER_BY_EMAIL = "FROM Usuario WHERE email = :email";
    private final static String GET_FACTOR_EMISION = "SELECT h.valor * c.factorEmision " +
            "FROM Huella h JOIN Actividad a ON h.idActividad.id = a.id " +
            "JOIN Categoria c ON a.idCategoria.id = c.id WHERE h.idUsuario.id = :idUsuario";
    private final static String GET_ALL_USERS = "FROM Usuario";


    public void insertUsuario(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }


    public List<Usuario> findAll() {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(GET_ALL_USERS);
        List<Usuario> users = query.getResultList();
        session.close();
        return users;
    }

    public Map<String, BigDecimal> calcularImpactoPorCategoriaPorIDUsuario(Usuario usuario) {
        Connection conn = Connection.getInstance();
        Session session = conn.getSession();

        String hql = "SELECT c.nombre, SUM(h.valor * c.factorEmision) " +
                "FROM Huella h " +
                "JOIN h.idActividad a " +
                "JOIN a.idCategoria c " +
                "WHERE h.idUsuario.id = :idUsuario " +
                "GROUP BY c.nombre";

        Query query = session.createQuery(hql);
        query.setParameter("idUsuario", usuario.getId());
        Map<String, BigDecimal> impactos = new HashMap<>();

        for (Object result : query.getResultList()) {
            Object[] row = (Object[]) result;

            String categoria = (String) row[0];
            BigDecimal impacto = (BigDecimal) row[1];

            impactos.put(categoria, impacto);
        }

        session.close();
        return impactos;
    }

    public List<BigDecimal> getFactorEmision(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(GET_FACTOR_EMISION);
        query.setParameter("idUsuario", user.getId());
        List<BigDecimal> factorEmision = query.getResultList();
        session.close();
        return factorEmision;
    }

    public Usuario updateUsuario(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public Usuario deleteUsuario(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.remove(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public Usuario findUserByID(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FIND_USER_BY_ID);
        query.setParameter("id", usuario.getId());
        Usuario user = (Usuario) query.getSingleResult();
        session.close();
        return user;
    }

    public Usuario findUserByEmail(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FIND_USER_BY_EMAIL);
        query.setParameter("email", usuario.getEmail());
        try {
            return (Usuario) query.getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } finally {
            session.close();
        }
    }


}
