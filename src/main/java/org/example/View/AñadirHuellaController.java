package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.App;
import org.example.Entities.Actividad;
import org.example.Entities.Huella;
import org.example.Services.ActividadService;
import org.example.Services.HuellaServices;
import org.example.Session.Session;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AñadirHuellaController extends Controller implements Initializable {
    @FXML
    Button buttonAñadirHuella;
    @FXML
    DatePicker datePicker;
    @FXML
    ImageView info;

    @FXML
    ComboBox <Actividad> comboBoxActividades;

    @FXML
    TextField valor;
    @FXML
    TextField unidadTextField;

    ActividadService actividadService = new ActividadService();
    HuellaServices huellaServices = new HuellaServices();

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

    public void changescenetoPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAPRINCIPAL, null);
    }

    public Huella recogerDatos() {
        Huella huella = new Huella();
        huella.setUnidad(unidadTextField.getText());
        try {
            if (valor.getText() != null && !valor.getText().isEmpty()) {
                huella.setValor(new BigDecimal(valor.getText()));
            } else {
                huella.setValor(null);
            }
        } catch (Exception e) {
            System.out.println("Error al procesar el valor: " + e.getMessage());
            huella.setValor(null);
        }
        if (comboBoxActividades.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Error: No se ha seleccionado ninguna actividad.");
            huella.setIdActividad(null);
        } else {
            huella.setIdActividad(
                    actividadService.getActividadByName(comboBoxActividades.getSelectionModel().getSelectedItem())
            );
        }
        if (datePicker.getValue() == null || datePicker.getValue().isAfter(LocalDate.now())) {
            System.out.println("Error: La fecha de la huella no es válida.");
        } else {
            huella.setFecha(datePicker.getValue());
            huella.setIdUsuario(Session.getInstancia().getUsuarioIniciado());
        }



        return huella;
    }


    //FALTA MOVERLO ESTO VA EN SERVICES
    public void insertarHuella() throws IOException {
        Huella huella = recogerDatos();
        boolean resultado = huellaServices.añadirHuella(huella);
        if (resultado) {
            System.out.println("Huella insertada correctamente.");
            changescenetoPantallaPrincipal();
            closeModalAñadirHuella();
        } else {
            System.out.println("Error al insertar la huella. Verifique los datos.");
        }
    }

    public void mostrarInformacion() {
        String reglas = "El valor debe ser un número mayor que 0. \nLa fecha no puede ser mayor al día de hoy. \nLa actividad debe ser seleccionada. \nLa unidad se seleccionara sola en funcion de tu Actividad";
        AppController.showInformationAlert("Información", reglas);
    }
}



