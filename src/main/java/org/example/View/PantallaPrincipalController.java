package org.example.View;

import com.itextpdf.text.DocumentException;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.converter.BigDecimalStringConverter;
import org.example.App;
import org.example.DAO.RecomendacionDAO;
import org.example.Entities.*;
import org.example.Services.*;
import org.example.Session.Session;
import org.example.Utils.Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class PantallaPrincipalController extends Controller implements Initializable {

    @FXML
    Button buttonRecomendacionesHuella;
    @FXML
    Button buttonRecomendacionesHabito;
    @FXML
    Button pdf;
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
    @FXML
    LineChart lineChart;
    @FXML
    ComboBox<String> comboBoxFiltro;

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
    @FXML
    TableColumn<Habito, String> FechaHabito;

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
    RecomendacionService recomendacionService = new RecomendacionService();

    //todo ESTE CONTROLLADOR TMBIEN TIENE LOS SERVICES APLICADOS COMPROBAR ERRORES

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setText("¡Bienvenido " + Session.getInstancia().getUsuarioIniciado().getNombre() + "!");
        comboBoxFiltro.getItems().addAll("Mes", "Semana", "Todos");
        comboBoxFiltro.setValue("Todos");
        comboBoxFiltro.setOnAction(event -> aplicarFiltro(comboBoxFiltro.getValue()));

        // Configuración del TableView de Huellas
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
        FechaHabito.setCellValueFactory(new PropertyValueFactory<>("ultimaFecha"));

        ActividadHabito.setCellValueFactory(cellData -> {
            Habito habito = cellData.getValue();
            Actividad actividad = habitoService.getActividadById(habito);
            return new SimpleStringProperty(actividad != null ? actividad.getNombre() : "Actividad no disponible");
        });

        List<Habito> habitos = habitoService.findByUser(Session.getInstancia().getUsuarioIniciado());
        habitoTableView.getItems().setAll(habitos);
        setupDeleteButtonHabito();

        // Configuración del PieChart
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

        // Inicializar LineChart con todos los datos
        aplicarFiltro("Todos");
    }

    private void aplicarFiltro(String filtro) {
        lineChart.getData().clear();
        List<Huella> huellas = huellaServices.findByUserID(Session.getInstancia().getUsuarioIniciado());
        List<BigDecimal> factoresEmision = userService.getFactorEmision(Session.getInstancia().getUsuarioIniciado());
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Factores de Emisión");
        if ("Todos".equals(filtro)) {
            huellas.sort(Comparator.comparing(Huella::getFecha));
            for (int i = 0; i < huellas.size(); i++) {
                Huella huella = huellas.get(i);
                String fecha = huella.getFecha().toString();
                BigDecimal factorEmisionL = (i < factoresEmision.size()) ? factoresEmision.get(i) : BigDecimal.ZERO;
                series.getData().add(new XYChart.Data<>(fecha, factorEmisionL.doubleValue()));
            }
        } else if ("Mes".equals(filtro)) {

            huellas.sort(Comparator.comparing(Huella::getFecha));
            for (int i = 0; i < huellas.size(); i++) {
                Huella huella = huellas.get(i);
                String fecha = huella.getFecha().toString();

                if (fecha.startsWith("2025-02")) {
                    BigDecimal factorEmisionL = (i < factoresEmision.size()) ? factoresEmision.get(i) : BigDecimal.ZERO;
                    series.getData().add(new XYChart.Data<>(fecha, factorEmisionL.doubleValue()));
                }
            }
        } else if ("Semana".equals(filtro)) {

            huellas.sort(Comparator.comparing(Huella::getFecha));
            for (int i = 0; i < huellas.size(); i++) {
                Huella huella = huellas.get(i);
                String fecha = huella.getFecha().toString();

                if (fecha.startsWith("2025-02-03")) {
                    BigDecimal factorEmisionL = (i < factoresEmision.size()) ? factoresEmision.get(i) : BigDecimal.ZERO;
                    series.getData().add(new XYChart.Data<>(fecha, factorEmisionL.doubleValue()));
                }
            }
        }

        lineChart.getData().add(series);
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

    //ACTUALIZAR LOS HABITOS
    private void setupTableViewHabito() {
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


    public Huella getHuellaSelccioandaTableViewHuella() {
        Huella huella = huellaTableView.getSelectionModel().getSelectedItem();
        if (huella != null) {
            System.out.println("Huella seleccionada: " + huella.toString());
            Actividad actividad = actividadService.getActividadById(huella);
            if (actividad != null) {
                huella.setIdActividad(actividad);
                System.out.println("ID de la actividad asignado a la huella: " + actividad.getId());
            } else {
                System.out.println("No se encontró una actividad para la huella seleccionada.");
            }
        } else {
            System.out.println("No hay huella seleccionada.");
        }
        RecomendacionDAO recomendacionDAO = new RecomendacionDAO();
        System.out.println(recomendacionDAO.findRecomendacionForHuella(huella));
        return huella;
    }

    public Habito getHabitoSelccioandaTableViewHabito() {
        Habito habito = habitoTableView.getSelectionModel().getSelectedItem();
        Actividad actividad = actividadService.getActividadByName(habitoTableView.getSelectionModel().getSelectedItem().getIdActividad());
        habito.setIdActividad(actividad);
        if (habito != null) {
            System.out.println("Habito seleccionado: " + habito.toString());

        }
        RecomendacionDAO recomendacionDAO = new RecomendacionDAO();
        System.out.println(recomendacionDAO.findRecomendacionForHabito(habito));
        return habito;
    }


    public void generarPDF() throws IOException, DocumentException {
        // Obtener los usuarios
        List<Usuario> users = userService.trearUsuarios();

        // Crear un objeto RecomendacionDAO para buscar recomendaciones
        RecomendacionDAO r = new RecomendacionDAO();

        // Obtener las huellas del usuario iniciado
        List<Huella> huellas = huellaServices.findByUserID(Session.getInstancia().getUsuarioIniciado());

        // Usamos un Set para evitar duplicados en las recomendaciones
        Set<String> recomendaciones = new HashSet<>();

        // Recorrer las huellas y obtener las recomendaciones para cada una
        for (Huella huella : huellas) {
            // Obtener las recomendaciones como una lista de objetos Recomendacion
            List<Recomendacion> recomendacionPorHuella = r.findRecomendacionForHuella(huella);

            // Convertir cada recomendacion de tipo Recomendacion a String y agregarla al Set
            for (Recomendacion recomendacion : recomendacionPorHuella) {
                recomendaciones.add(recomendacion.getDescripcion()); // Agregar solo si no está repetida
            }
        }

        // Llamar a la función de generación de PDF pasándole los usuarios y las recomendaciones
        Utils.generarReportePDF(users, new ArrayList<>(recomendaciones)); // Convertir Set a List si es necesario
    }



    @Override
    public void onOpen(Object input) throws IOException {
    }

    public void openModalAñadirHuella() throws Exception {
        App.currentController.openModal(Scenes.HUELLA, "Añadir Huella", this, null);
    }

    public void openModalRecomendacionesHuellaSeleccioanda() throws Exception {
        try {
            App.currentController.openModal(Scenes.RECOMENDACIONHUELLAS, "Recomendaciones", this, getHuellaSelccioandaTableViewHuella());
        } catch (NullPointerException e) {
            AppController.showErrorAlertNoSeleccionado();

        }
    }

    public void openModalAñadirHabito() throws Exception {
        App.currentController.openModal(Scenes.HABITO, "Añadir Habito", this, null);
    }

    public void changescenetoPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAPRINCIPAL, null);
    }

    public void openModalPerfil() throws Exception {
        App.currentController.openModal(Scenes.ACTUALIZARUSUARIO, "Actualizar Usuario", this, null);
    }

    public void openModalRecomendacionesHabito() throws Exception {
        try {
            App.currentController.openModal(Scenes.RECOMENDACIONHABITOS, "Actualizar Usuario", this, getHabitoSelccioandaTableViewHabito());
        } catch (NullPointerException e) {
            AppController.showErrorAlertNoSeleccionado();
        }
    }

    public void changescenetoWelcomeLogOut() throws IOException {
        Session.getInstancia().logOut();
        App.currentController.changeScene(Scenes.PRIMARY, null);
    }

}