package org.example.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.Entities.Usuario;
import org.example.Services.UserService;
import org.example.View.Controller;
import org.example.View.Scenes;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;

public class RegisterController extends Controller implements Initializable {

    @FXML
    Button btnRegister;
    @FXML
    Button btnLogin;
    @FXML
    TextField txtUser;
    @FXML
    TextField textEmail;
    @FXML
    PasswordField contraseña;
    UserService userService = new UserService();

    //todo ESTE CONTROLLADOR TMBIEN TIENE LOS SERVICES APLICADOS COMPROBAR ERRORES
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void onOpen(Object input) throws IOException {
    }

    public Usuario recogerDatos() {
        String usuario = txtUser.getText();
        String password = contraseña.getText();
        String email = textEmail.getText();
        return new Usuario(usuario, email, password, Instant.now());
    }
    public void registrar() throws IOException {
        if (userService.registerUser(recogerDatos())){
           changeSceneToPantallaPricipal();
        }else {
            System.out.println("ERROR");
        }
    }

    public void changeSceneToPantallaPricipal() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAPRINCIPAL, null);
    }
    public void changeSceneToLogin() throws IOException {
        App.currentController.changeScene(Scenes.LOGIN, null);
    }
}
