package org.example.DAO;

import jakarta.persistence.Query;
import org.example.Entities.Usuario;
import org.example.Session.Connection;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

public class UserDAO {

    private final static String findUserByID = "FROM Usuario WHERE id = :id";
    private final static String findUserByEmail = "FROM Usuario WHERE email = :email";
    private final static String getFactorEmision = "SELECT h.valor * c.factorEmision FROM Huella h JOIN Actividad a ON h.idActividad.id = a.id JOIN Categoria c ON a.idCategoria.id = c.id WHERE h.idUsuario.id = :idUsuario";


    public void insertUsuario(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }

    public List<BigDecimal> getFactorEmision(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(getFactorEmision);
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
        Query query = session.createQuery(findUserByID);
        query.setParameter("id", usuario.getId());
        Usuario user = (Usuario) query.getSingleResult();
        session.close();
        return user;
    }

    //GET SINGLE RESULT SI NO ENCUENTRA AL DATO LANZA UNA EXCEPCION DE NULO, CONTROLARLA CON TRY CATCH
    public Usuario findUserByEmail(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(findUserByEmail);
        query.setParameter("email", usuario.getEmail());
        try {
            Usuario user = (Usuario) query.getSingleResult();
            return user;
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } finally {
            session.close();
        }
    }
}
