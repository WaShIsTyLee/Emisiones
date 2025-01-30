package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.Entities.Usuario;
import org.example.Services.UserService;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {

    @FXML
    Button btnLogin;
    @FXML
    PasswordField txtPassword;
    @FXML
    TextField txtUser;
    UserService userService = new UserService();

    //todo ESTE CONTROLLADOR TMBIEN TIENE LOS SERVICES APLICADOS COMPROBAR ERRORES


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void onOpen(Object input) throws IOException {
    }

    public Usuario recogerDatos() {
        String email = txtUser.getText();
        String password = txtPassword.getText();
        return new Usuario(email, password);
    }

    public void iniciarSesion() throws IOException {
        if (userService.logUser(recogerDatos())) {
            changeSceneToPantallaPricipal();
        } else {
            // ERROR
        }
    }

    public void changeSceneToPantallaPricipal() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAPRINCIPAL, null);
    }
}
