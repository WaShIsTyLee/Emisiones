package org.example.DAO;

import jakarta.persistence.Query;
import org.example.Entities.Usuario;
import org.example.Session.Connection;
import org.hibernate.Session;

import java.util.List;

public class UserDAO {

    private final static String findUserByID = "FROM Usuario WHERE id = :id";
    private final static String findUserByEmail = "FROM Usuario WHERE email = :email";

    public void insertUsuario(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
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
