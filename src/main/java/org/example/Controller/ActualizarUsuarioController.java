package org.example.Controller;

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
import org.example.Utils.Utils;
import org.example.View.Controller;
import org.example.View.Scenes;

import java.io.IOException;
import java.net.URL;
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
        Usuario usuarioActual = Session.getInstancia().getUsuarioIniciado();

        txtUser.setText(usuarioActual.getNombre());
        email.setText(usuarioActual.getEmail());
        contrasena.setText("****"); // Indicador visual de contraseña oculta

        text.setText("¡Vamos a actualizar tus datos " + usuarioActual.getNombre() + "!");
    }

    public Usuario recogerDatos() throws IOException {
        Usuario usuarioActual = Session.getInstancia().getUsuarioIniciado();

        String nombre = txtUser.getText().trim();
        String emailText = email.getText().trim();
        String contrasenaText = contrasena.getText().trim();

        if (nombre.isEmpty()) {
            nombre = usuarioActual.getNombre(); // Mantener nombre actual si no se cambia
        }
        if (emailText.isEmpty()) {
            emailText = usuarioActual.getEmail(); // Mantener email actual si no se cambia
        }
        if (contrasenaText.equals("****") || contrasenaText.isEmpty()) {
            contrasenaText = usuarioActual.getContraseña(); // Mantener contraseña actual
        } else {
            contrasenaText = Utils.hashPassword(contrasenaText); // Encriptar nueva contraseña
        }

        Usuario user = new Usuario(nombre, emailText, contrasenaText, usuarioActual.getFechaRegistro());
        user.setId(usuarioActual.getId());

        return user;
    }

    public void Actualizar() throws IOException {
        Usuario user = recogerDatos();

        if (userService.actualizarUsuario(user)) {
            Session.getInstancia().logOut();
            Session.getInstancia().logIn(user);
            changescenetoPantallaPrincipal();
        } else {
            System.err.println("Error al actualizar el usuario.");
        }
    }

    public void changescenetoPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAPRINCIPAL, null);
        Stage stage = (Stage) buttonActualizar.getScene().getWindow();
        stage.close();
    }

}
