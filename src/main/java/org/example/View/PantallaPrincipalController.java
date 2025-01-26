package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.example.App;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PantallaPrincipalController extends Controller implements Initializable {

    @FXML
    Button btnAnadirHuella;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void onOpen(Object input) throws IOException {
    }

    public void openModalAñadirHuella() throws Exception {
        App.currentController.openModal(Scenes.HUELLA, "Añadir Huella", this, null);
    }
}
