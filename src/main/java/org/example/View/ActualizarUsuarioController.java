package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.App;
import org.example.Entities.Usuario;
import org.example.Services.UserService;
import org.example.Session.Session;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;

public class ActualizarUsuarioController extends Controller implements Initializable {
    @FXML
    Text text;
    @FXML
    Button buttonActualizar;
    @FXML
    TextField txtUser;
    @FXML
    TextField email;
    @FXML
    TextField contrasena;

    UserService userService = new UserService();


    @Override
    public void onOpen(Object input) throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUser.setText(Session.getInstancia().getUsuarioIniciado().getNombre());
        email.setText(Session.getInstancia().getUsuarioIniciado().getEmail());
        contrasena.setText(Session.getInstancia().getUsuarioIniciado().getContraseña());
        text.setText("¡Vamos a actualizar tus datos " + Session.getInstancia().getUsuarioIniciado().getNombre() + "!");
    }

    public Usuario recogerDatos() throws IOException {
        String nombre = txtUser.getText();
        String emailText = email.getText();
        String contrasenaText = contrasena.getText();
        Usuario user = new Usuario(nombre, emailText, contrasenaText, Session.getInstancia().getUsuarioIniciado().getFechaRegistro());
        user.setId(Session.getInstancia().getUsuarioIniciado().getId());
        return user;
    }

    public void Actualizar() throws IOException {
        Usuario user = recogerDatos();
        userService.actualizarUsuario(user);
        Session.getInstancia().logOut();
        Session.getInstancia().logIn(user);
        changescenetoPantallaPrincipal();
    }
    public void changescenetoPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAPRINCIPAL, null);
        Stage stage = (Stage) buttonActualizar.getScene().getWindow();
        stage.close();
    }
}
