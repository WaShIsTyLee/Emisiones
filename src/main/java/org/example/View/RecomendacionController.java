package org.example.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.Entities.Habito;
import org.example.Entities.Recomendacion;
import org.example.Services.HabitoService;
import org.example.Services.RecomendacionService;
import org.example.Session.Session;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//todo HA FALTA DE TOCAR LA VISTA ESTA CLASE YA TIENE CONTROL DE SERVICES

public class RecomendacionController extends Controller implements Initializable {

    @FXML
    private TilePane tilePaneRecomendaciones;

    private HabitoService habitoService = new HabitoService();
    private RecomendacionService recomendacionService = new RecomendacionService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarRecomendaciones();
    }

    private void cargarRecomendaciones() {
        List<Recomendacion> listaRecomendaciones = new ArrayList<>();
        List<Habito> habitosUsuario = habitoService.findByUser(Session.getInstancia().getUsuarioIniciado());
        recomendacionService.findRecomendacionesForUser(habitosUsuario, listaRecomendaciones);


        for (Recomendacion recomendacion : listaRecomendaciones) {
            VBox itemRecomendacion = new VBox();
            itemRecomendacion.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 10; -fx-border-radius: 5;");

            Text descripcion = new Text(recomendacion.getDescripcion());
            descripcion.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Text impacto = new Text("Impacto estimado: " + recomendacion.getImpactoEstimado());
            impacto.setStyle("-fx-font-size: 12px; -fx-fill: gray;");

            itemRecomendacion.getChildren().addAll(descripcion, impacto);
            tilePaneRecomendaciones.getChildren().add(itemRecomendacion);
        }
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }
}
