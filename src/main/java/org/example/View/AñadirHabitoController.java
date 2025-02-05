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
    DatePicker datePicker;
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
        Habito habito = new Habito();

        // Obtener y validar el tipo
        String tipo = (String) comboBoxTipo.getValue();
        if (tipo == null || tipo.isEmpty()) {
            AppController.showErrorAlertNoSeleccionado();
            return null;
        }
        habito.setTipo(tipo);

        // Obtener y validar la frecuencia
        try {
            if (frecuencia.getText() != null && !frecuencia.getText().isEmpty()) {
                habito.setFrecuencia(Integer.parseInt(frecuencia.getText()));
            } else {
                habito.setFrecuencia(0); // O algún valor por defecto si es necesario
            }
        } catch (NumberFormatException e) {
            System.out.println("Error al procesar la frecuencia: " + e.getMessage());
            AppController.showErrorAlertNoSeleccionado();
            return null;
        }

        // Obtener y validar la actividad seleccionada
        Actividad actividadSeleccionada = comboBoxActividades.getSelectionModel().getSelectedItem();
        if (actividadSeleccionada == null) {
            AppController.showErrorAlertNoSeleccionado();
            return null;
        } else {
            Actividad actividad = actividadService.getActividadByName(actividadSeleccionada);
            habito.setIdActividad(actividad);
        }

        // Obtener y validar la fecha seleccionada
        LocalDate fechaSeleccionada = datePicker.getValue();
        if (fechaSeleccionada == null || fechaSeleccionada.isAfter(LocalDate.now())) {
            AppController.showErrorAlertNoSeleccionado();
            return null;
        } else {
            habito.setUltimaFecha(fechaSeleccionada);
        }

        // Asociar usuario actual
        habito.setIdUsuario(Session.getInstancia().getUsuarioIniciado());

        // Crear el ID compuesto del hábito
        HabitoId id = new HabitoId();
        id.setIdActividad(habito.getIdActividad().getId());
        id.setIdUsuario(Session.getInstancia().getUsuarioIniciado().getId());
        habito.setId(id);

        return habito;
    }


    public void insertarHabito() throws IOException {
        try {
            Habito habito = recogerDatosHabito();
            habitoService.insertarHabito(habito);
            changescenetoPantallaPrincipal();
            closeModalAñadirHuella();
        }catch (Exception e) {
            AppController.showErrorAlertHuellaNoCompletada();
        }

    }

    public void changescenetoPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAPRINCIPAL, null);
    }

    public void closeModalAñadirHuella() throws IOException {
        Stage stage = (Stage) buttonAñadirHabito.getScene().getWindow();
        stage.close();
    }
}
