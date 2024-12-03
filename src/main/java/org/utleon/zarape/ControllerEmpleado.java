package org.utleon.zarape;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControllerEmpleado {

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TableColumn<?, ?> colActivo;

    @FXML
    private TableColumn<?, ?> colApellido;

    @FXML
    private TableColumn<?, ?> colCiudad;

    @FXML
    private TableColumn<?, ?> colContrasenia;

    @FXML
    private TableColumn<?, ?> colIdEmpleado;

    @FXML
    private TableColumn<?, ?> colNombre;

    @FXML
    private TableColumn<?, ?> colSucursal;

    @FXML
    private TableColumn<?, ?> colTelefono;

    @FXML
    private TableColumn<?, ?> colUsuario;

    @FXML
    private TableView<?> tblSucursales;

    @FXML
    private TextField txtActivo;

    @FXML
    private TextField txtApellido;

    @FXML
    private ComboBox<?> txtCiudad;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private CheckBox txtEstatus;

    @FXML
    private TextField txtIdEmpleado;

    @FXML
    private Label txtLabelHeader;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtSucursal;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtUsuario;

}
