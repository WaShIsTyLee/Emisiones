package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.DAO.ActividadDAO;
import org.example.Entities.Actividad;
import org.example.Entities.Huella;
import org.example.Services.ActividadService;
import org.example.Session.Session;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AñadirHuellaController extends Controller implements Initializable {
    @FXML
    Button buttonAñadirHuella;

    @FXML
    ComboBox <Actividad> comboBoxActividades;

    @FXML
    TextField valor;
    @FXML
    TextField unidadTextField;

    ActividadService actividadService = new ActividadService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxActividades.getItems().setAll(actividadService.getAllActividades());
        comboBoxActividades.setCellFactory(new Callback<ListView<Actividad>, ListCell<Actividad>>() {
            @Override
            public ListCell<Actividad> call(ListView<Actividad> param) {
                return new ListCell<Actividad>() {
                    @Override
                    protected void updateItem(Actividad item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getNombre());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        comboBoxActividades.setButtonCell(new ListCell<Actividad>() {
            @Override
            protected void updateItem(Actividad item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getNombre());
                } else {
                    setText(null);
                }
            }
        });
        comboBoxActividades.setOnAction(event -> {
            Actividad actividadSeleccionada = comboBoxActividades.getSelectionModel().getSelectedItem();
            if (actividadSeleccionada != null) {
                String unidad = actividadService.getUnidadByActividad(actividadSeleccionada);
                unidadTextField.setText(unidad);
            }
        });
    }



    @Override
    public void onOpen(Object input) throws IOException {
    }

    public void closeModalAñadirHuella() throws IOException {
        Stage stage = (Stage) buttonAñadirHuella.getScene().getWindow();
        stage.close();
    }

    public Huella recogerDatos() {
        Huella huella = new Huella();
        huella.setUnidad(unidadTextField.getText());
        huella.setValor(new BigDecimal(valor.getText()));
        huella.setIdActividad(actividadService.getActividadByName(comboBoxActividades.getSelectionModel().getSelectedItem()));
        huella.setFecha(LocalDate.now());
        huella.setIdUsuario(Session.getInstancia().getUsuarioIniciado());
        return huella;
    }

    //FALTA MOVERLO ESTO VA EN SERVICES
    public void insertarHuella() throws IOException {
        Huella huella = recogerDatos();
        ActividadDAO actividadDAO = new ActividadDAO();
        actividadDAO.insertaHuella(huella);
        closeModalAñadirHuella();
    }


}
