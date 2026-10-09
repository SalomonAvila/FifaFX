package org.example.fifafx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FifaController {

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfDorsal;

    @FXML
    private ComboBox<String> cbPosicion;

    @FXML
    private ComboBox<String> cbEquipo;

    @FXML
    private Button btnGuardar;

    @FXML
    private TableView<Jugador> tvJugadores;

    @FXML
    private TableColumn<Jugador, String> colNombre;

    @FXML
    private TableColumn<Jugador, String> colPosicion;

    @FXML
    private TableColumn<Jugador, Integer> colDorsal;

    @FXML
    private TableColumn<Jugador, String> colEquipo;

    @FXML
    private TreeTableView<String> ttvArbolEquipos;

    @FXML
    private TreeTableColumn<String, String> colArbolNombre;

    @FXML
    private TreeTableColumn<String, String> colArbolDetalle;

    private ObservableList<Jugador> listaJugadores = FXCollections.observableArrayList();
    private TreeItem<String> raizLiga;

    private ObservableList<String> equiposDisponibles = FXCollections.observableArrayList(
            "Sistemas FC", "Civil United", "Medicina AC", "Derecho City"
    );
    private ObservableList<String> posicionesDisponibles = FXCollections.observableArrayList(
            "Portero", "Defensa", "Mediocampista", "Delantero"
    );

    @FXML
    public void initialize() {
        cbEquipo.setItems(equiposDisponibles);
        cbEquipo.getSelectionModel().selectFirst();

        cbPosicion.setItems(posicionesDisponibles);
        cbPosicion.getSelectionModel().selectFirst();

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPosicion.setCellValueFactory(new PropertyValueFactory<>("posicion"));
        colDorsal.setCellValueFactory(new PropertyValueFactory<>("dorsal"));
        colEquipo.setCellValueFactory(new PropertyValueFactory<>("equipo"));

        tvJugadores.setItems(listaJugadores);

        configurarArbol();
    }

    private void configurarArbol() {
        raizLiga = new TreeItem<>("Liga Universitaria");
        raizLiga.setExpanded(true);

        for (String nombreEquipo : equiposDisponibles) {
            TreeItem<String> nodoEquipo = new TreeItem<>(nombreEquipo);
            nodoEquipo.setExpanded(true);

            for (String nombrePosicion : posicionesDisponibles) {
                TreeItem<String> nodoPosicion = new TreeItem<>(nombrePosicion);
                nodoPosicion.setExpanded(true);
                nodoEquipo.getChildren().add(nodoPosicion);
            }

            raizLiga.getChildren().add(nodoEquipo);
        }

        ttvArbolEquipos.setRoot(raizLiga);

        colArbolNombre.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getValue())
        );

        colArbolDetalle.setCellValueFactory(param -> {
            TreeItem<String> nodoActual = param.getValue();

            if (nodoActual.getParent() == null) {
                return new SimpleStringProperty("Liga Principal");
            }
            else if (nodoActual.getParent().equals(raizLiga)) {
                return new SimpleStringProperty("Equipo");
            }
            else if (nodoActual.getParent().getParent().equals(raizLiga)) {
                return new SimpleStringProperty("Posición");
            }
            else {
                return new SimpleStringProperty("Jugador");
            }
        });
    }

    @FXML
    void guardarJugador(ActionEvent event) {
        if (tfNombre.getText().isEmpty() || tfDorsal.getText().isEmpty() ||
                cbEquipo.getValue() == null || cbPosicion.getValue() == null) {
            return;
        }

        String nombre = tfNombre.getText();
        int dorsal = Integer.parseInt(tfDorsal.getText());
        String equipoSeleccionado = cbEquipo.getValue();
        String posicionSeleccionada = cbPosicion.getValue();

        Jugador nuevoJugador = new Jugador(nombre, posicionSeleccionada, dorsal, equipoSeleccionado);
        listaJugadores.add(nuevoJugador);

        TreeItem<String> hojaJugador = new TreeItem<>(nombre + " (Dorsal: " + dorsal + ")");

        for (TreeItem<String> nodoEquipo : raizLiga.getChildren()) {
            if (nodoEquipo.getValue().equals(equipoSeleccionado)) {
                for (TreeItem<String> nodoPosicion : nodoEquipo.getChildren()) {
                    if (nodoPosicion.getValue().equals(posicionSeleccionada)) {
                        nodoPosicion.getChildren().add(hojaJugador);
                        break;
                    }
                }
                break;
            }
        }

        tfNombre.clear();
        tfDorsal.clear();
        tfNombre.requestFocus();
    }
}
