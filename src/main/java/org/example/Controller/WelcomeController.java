package org.example.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.example.App;
import org.example.View.Controller;
import org.example.View.Scenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController extends Controller implements Initializable {
    @FXML
    Button iniciarSesion;
    @FXML
    Button registrarse;

    public void changeSceneIniciarSesion() throws IOException {
        App.currentController.changeScene(Scenes.LOGIN, null);
    }
    public void changeSceneRegistrarse() throws IOException {
        App.currentController.changeScene(Scenes.REGISTER, null);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }


}
