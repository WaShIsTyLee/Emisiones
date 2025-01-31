package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.App;
import org.example.Entities.Actividad;
import org.example.Entities.Habito;
import org.example.Entities.HabitoId;
import org.example.Services.ActividadService;
import org.example.Services.HabitoService;
import org.example.Session.Session;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AñadirHabitoController extends Controller implements Initializable {
    @FXML
    ComboBox<Actividad> comboBoxActividades;
    @FXML
    TextField frecuencia;
    @FXML
    Button buttonAñadirHabito;
    @FXML
    ComboBox comboBoxTipo;
    ActividadService actividadService = new ActividadService();
    HabitoService habitoService = new HabitoService();

    //todo ESTE CONTROLLADOR TMBIEN TIENE LOS SERVICES APLICADOS COMPROBAR ERRORES
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
        comboBoxTipo.getItems().addAll("Semanal", "Diario", "Mensual", "Anual");
    }

    @Override
    public void onOpen(Object input) throws IOException {
    }

    public Habito recogerDatosHabito() {
        String tipo = (String) comboBoxTipo.getValue();
        int frecuencia = Integer.parseInt(this.frecuencia.getText());
        Habito habito = new Habito();
        Actividad actividad = new Actividad();
        actividad = actividadService.getActividadByName(comboBoxActividades.getSelectionModel().getSelectedItem());
        habito.setIdActividad(actividad);
        habito.setFrecuencia(frecuencia);
        habito.setTipo(tipo);
        habito.setIdUsuario(Session.getInstancia().getUsuarioIniciado());
        habito.setUltimaFecha(LocalDate.now());
        HabitoId id = new HabitoId();
        id.setIdActividad(actividad.getId());
        id.setIdUsuario(Session.getInstancia().getUsuarioIniciado().getId());
        habito.setId(id);
        return habito;
    }

    public void insertarHabito() throws IOException {
        Habito habito = recogerDatosHabito();
        habitoService.insertarHabito(habito);
        changescenetoPantallaPrincipal();
        closeModalAñadirHuella();
    }

    public void changescenetoPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAPRINCIPAL, null);
    }

    public void closeModalAñadirHuella() throws IOException {
        Stage stage = (Stage) buttonAñadirHabito.getScene().getWindow();
        stage.close();
    }
}
