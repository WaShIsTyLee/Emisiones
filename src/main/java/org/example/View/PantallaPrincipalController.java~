package org.example.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.converter.BigDecimalStringConverter;
import org.example.App;
import org.example.Entities.Actividad;
import org.example.Entities.Habito;
import org.example.Entities.Huella;
import org.example.Services.ActividadService;
import org.example.Services.HabitoService;
import org.example.Services.HuellaServices;
import org.example.Services.UserService;
import org.example.Session.Session;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PantallaPrincipalController extends Controller implements Initializable {

    @FXML
    Text text;
    @FXML
    ImageView imageLogOut;
    @FXML
    Button btnAnadirHuella;
    @FXML
    Button perfil;
    @FXML
    Button btnAnadirHabito;
    @FXML
    Button btnRecomendaciones;
    @FXML
    PieChart pieChart;

    //TABLEVIEW DE HABITOS
    @FXML
    TableView<Habito> habitoTableView;
    @FXML
    TableColumn<Habito, String> Frecuecncia;
    @FXML
    TableColumn<Habito, String> Tipo;
    @FXML
    TableColumn<Habito, String> ActividadHabito;
    @FXML
    TableColumn<Habito, Void> EliminarHabito;

    //TABLEVIEW DE HUELLAS
    @FXML
    TableView<Huella> huellaTableView;
    @FXML
    TableColumn<Huella, BigDecimal> Valor;
    @FXML
    TableColumn<Huella, String> Unidad;
    @FXML
    TableColumn<Huella, String> Fecha;
    @FXML
    TableColumn<Huella, String> Actividad;
    @FXML
    TableColumn<Huella, Void> Eliminar;


    HuellaServices huellaServices = new HuellaServices();
    HabitoService habitoService = new HabitoService();
    ActividadService actividadService = new ActividadService();
    UserService userService = new UserService();

    //todo ESTE CONTROLLADOR TMBIEN TIENE LOS SERVICES APLICADOS COMPROBAR ERRORES

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setText("¡Bienvenido " + Session.getInstancia().getUsuarioIniciado().getNombre() + "!");
        // CONFIGURACION DEL TABLEVIEW DE HUELLAS
        Valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        Unidad.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        Fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        Actividad.setCellValueFactory(cellData -> {
            Huella huella = cellData.getValue();
            Actividad actividad = actividadService.getActividadById(huella);
            return new SimpleStringProperty(actividad != null ? actividad.getNombre() : "Actividad no disponible");
        });

        List<Huella> huellas = huellaServices.findByUserID(Session.getInstancia().getUsuarioIniciado());
        huellaTableView.getItems().setAll(huellas);
        setupTableView();
        setupDeleteButton();

        // CONFIGURACION DEL TABLEVIEW DE HÁBITOS
        Frecuecncia.setCellValueFactory(new PropertyValueFactory<>("frecuencia"));
        Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        ActividadHabito.setCellValueFactory(cellData -> {
            Habito habito = cellData.getValue();
            Actividad actividad = habitoService.getActividadById(habito);
            return new SimpleStringProperty(actividad != null ? actividad.getNombre() : "Actividad no disponible");
        });

        List<Habito> habitos = habitoService.findByUser(Session.getInstancia().getUsuarioIniciado());
        habitoTableView.getItems().setAll(habitos);
        setupDeleteButtonHabito();

        // CONFIGURACIÓN DEL PIECHART CON LOS NOMBRES DE LAS ACTIVIDADES
        pieChart.getData().clear();
        List<Huella> huellasPieChart = huellaServices.findByUserID(Session.getInstancia().getUsuarioIniciado());


        List<BigDecimal> factorEmision = userService.getFactorEmision(Session.getInstancia().getUsuarioIniciado());

        double total = huellasPieChart.stream().mapToDouble(h -> h.getValor().doubleValue()).sum();

        for (int i = 0; i < huellasPieChart.size(); i++) {
            Huella huella = huellasPieChart.get(i);
            Actividad actividad = actividadService.getActividadById(huella);
            String nombreActividad = (actividad != null) ? actividad.getNombre() : "Sin actividad";
            BigDecimal valorEmision = (i < factorEmision.size()) ? factorEmision.get(i) : BigDecimal.ZERO;
            double val = valorEmision.doubleValue();
            double porcentaje = (val / total) * 100;
            pieChart.getData().add(new PieChart.Data(
                    String.format("%s: %.2f kg CO₂ (%.1f%%)", nombreActividad, val, porcentaje),
                    val
            ));
        }

    }


    private void setupTableView() {
        Valor.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        Valor.setOnEditCommit(event -> {
            Huella huella = event.getRowValue();
            try {
                BigDecimal nuevoValor = new BigDecimal(event.getNewValue().toString());
                huella.setValor(nuevoValor);
                huellaServices.actualizarHuella(huella);
                try {
                    changescenetoPantallaPrincipal();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Valor actualizado a: " + nuevoValor);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el valor a BigDecimal: " + event.getNewValue());
            }
        });
    }

    private void setupDeleteButton() {
        Eliminar.setCellFactory(param -> new javafx.scene.control.TableCell<>() {
            private final Button deleteButton = new Button("Eliminar");

            {
                deleteButton.setOnAction(event -> {
                    Huella huella = getTableView().getItems().get(getIndex());
                    huellaServices.delete(huella);
                    getTableView().getItems().remove(huella);
                    try {
                        changescenetoPantallaPrincipal();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void setupDeleteButtonHabito() {
        EliminarHabito.setCellFactory(param -> new javafx.scene.control.TableCell<>() {
            private final Button deleteButton = new Button("Eliminar");

            {
                deleteButton.setOnAction(event -> {
                    Habito habito = getTableView().getItems().get(getIndex());
                    habitoService.delete(habito);
                    getTableView().getItems().remove(habito);
                    try {
                        changescenetoPantallaPrincipal();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }


    @Override
    public void onOpen(Object input) throws IOException {
    }

    public void openModalAñadirHuella() throws Exception {
        App.currentController.openModal(Scenes.HUELLA, "Añadir Huella", this, null);
    }

    public void openModalRecomendaciones() throws Exception {
        App.currentController.openModal(Scenes.RECOMENDACION, "Recomendaciones", this, null);
    }

    public void openModalAñadirHabito() throws Exception {
        App.currentController.openModal(Scenes.HABITO, "Añadir Habito", this, null);
    }

    public void changescenetoPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAPRINCIPAL, null);
    }
    public void openModalPerfil() throws Exception {
        //App.currentController.openModal(Scenes.HABITO, "Añadir Habito", this, null);
    }
    public void changescenetoWelcomeLogOut() throws IOException {
        Session.getInstancia().logOut();
        App.currentController.changeScene(Scenes.PRIMARY, null);
    }

}
