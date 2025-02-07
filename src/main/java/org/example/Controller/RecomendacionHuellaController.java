package org.example.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.DAO.RecomendacionDAO;
import org.example.Entities.Huella;
import org.example.Entities.Recomendacion;
import org.example.View.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RecomendacionHuellaController extends Controller implements Initializable {
    @FXML
    VBox panelInfo;

    @Override
    public void onOpen(Object input) throws IOException {

        if (panelInfo.getScene() != null) {
            Stage stage = (Stage) panelInfo.getScene().getWindow();
            stage.setWidth(630);
            stage.setHeight(450);
            stage.setResizable(false);
        } else {

            panelInfo.sceneProperty().addListener((observable, oldScene, newScene) -> {
                if (newScene != null) {
                    Stage stage = (Stage) newScene.getWindow();
                    stage.setWidth(900);
                    stage.setHeight(600);
                    stage.setResizable(false);
                }
            });
        }


        Huella huella = (Huella) input;
        System.out.println(huella);

        RecomendacionDAO recomendacionDAO = new RecomendacionDAO();
        List<Recomendacion> recomendaciones = recomendacionDAO.findRecomendacionForHuella(huella);

        System.out.println(recomendaciones);

        panelInfo.getChildren().clear();

        Label huellaLabel = new Label("🌍 Recomendaciones para tu huella:");
        huellaLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
        panelInfo.getChildren().add(huellaLabel);

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #ccc; -fx-padding: 5px;");
        panelInfo.getChildren().add(separator);

        if (recomendaciones.isEmpty()) {
            Label noRecomendaciones = new Label("🚫 No hay recomendaciones disponibles.");
            noRecomendaciones.setStyle("-fx-font-size: 14px; -fx-text-fill: #777;");
            panelInfo.getChildren().add(noRecomendaciones);
        } else {
            Label recomendacionTitle = new Label("💡 Recomendaciones:");
            recomendacionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
            panelInfo.getChildren().add(recomendacionTitle);
            for (Recomendacion recomendacion : recomendaciones) {
                HBox card = new HBox();
                card.setSpacing(10);
                card.setPadding(new Insets(10));
                card.setAlignment(Pos.CENTER_LEFT);
                card.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-border-color: #ddd; -fx-padding: 10px;");
                Label icono = new Label("✔");
                icono.setStyle("-fx-font-size: 18px; -fx-text-fill: #4CAF50;"); // Verde
                Label recomendacionLabel = new Label(recomendacion.getDescripcion());
                recomendacionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
                Label impactoLabel = new Label(getImpactoTexto(recomendacion.getImpactoEstimado()));
                impactoLabel.setStyle(getImpactoStyle(recomendacion.getImpactoEstimado()));
                card.getChildren().addAll(icono, recomendacionLabel, impactoLabel);
                panelInfo.getChildren().add(card);
            }
        }
    }


    private String getImpactoTexto(BigDecimal impacto) {
        return "Ahorro de CO₂: " + impacto + " kg";
    }


    private String getImpactoStyle(BigDecimal impacto) {
        return "-fx-background-color: #77dd77; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
