package org.utleon.zarape;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.utleon.zarape.model.Ciudad;
import org.utleon.zarape.model.Cliente;
import org.utleon.zarape.model.Persona;
import org.utleon.zarape.model.Usuario;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ControllerCliente {

    @FXML
    private TextField txtIdCl, txtNom, txtApe, txtTelUsr, txtIdPer, txtNomUsr, txtCont, txtIdUsr;

    @FXML
    private ComboBox<Ciudad> selector;

    @FXML
    private Button btnAdd, btnCancelar, btnUpdate, btndelete;

    @FXML
    private TableView<Cliente> tabCl;

    @FXML
    private TableColumn<Cliente, Integer> colIdCliente;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colApellidos;

    @FXML
    private TableColumn<Cliente, String> colTelefono;

    @FXML
    private TableColumn<Cliente, String> colUsername;

    @FXML
    private TableColumn<Cliente, String> colContrasenia;

    private ObservableList<Ciudad> ciudades;
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private final Gson gson = new Gson();
    int flag=0;
    @FXML
    private void initialize() {
        colIdCliente.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getIdCliente()));
        colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPersona().getNombre()));
        colApellidos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPersona().getApellidos()));
        colTelefono.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPersona().getTelefono()));
        colUsername.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getUser().getNombre()));
        colContrasenia.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getUser().getContrasenia()));


        tabCl.setItems(clientes);
        tabCl.setOnMouseClicked(this::modificarCl);
        selector.setConverter(new StringConverter<Ciudad>() {
            @Override
            public String toString(Ciudad ciudad) {
                return ciudad != null ? ciudad.getNombre() : "";
            }

            @Override
            public Ciudad fromString(String nombre) {
                // Este método no se usará en este caso
                return null;
            }
        });
        selector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Ciudad seleccionada: " + newValue.getNombre());
            }
        });
        loadCiudades();
        imprimirTablaCl();
    }

    @FXML
    private void imprimirTablaCl() {
        try {
            URL url = new URL("http://localhost:8080/Mexicode_Proyecto/api/cliente/getAll");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Type listType = new TypeToken<List<Cliente>>() {}.getType();
            List<Cliente> clienteList = gson.fromJson(reader, listType);
            reader.close();

            clientes.clear();
            clientes.addAll(clienteList);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarCl(MouseEvent event) {
        Cliente selectedCliente = tabCl.getSelectionModel().getSelectedItem();
        if (selectedCliente == null) return;

        txtIdCl.setText(String.valueOf(selectedCliente.getIdCliente()));
        txtNom.setText(selectedCliente.getPersona().getNombre());
        txtApe.setText(selectedCliente.getPersona().getApellidos());
        txtTelUsr.setText(selectedCliente.getPersona().getTelefono());
        txtIdPer.setText(String.valueOf(selectedCliente.getPersona().getIdPersona()));
        txtNomUsr.setText(selectedCliente.getUser().getNombre());
        txtCont.setText(selectedCliente.getUser().getContrasenia());
        txtIdUsr.setText(""+selectedCliente.getUser().getIdUsuario());
        selector.setValue(findCiudadById(selectedCliente.getPersona().getIdCiudad()));
        flag=1;

    }

    @FXML
    public void insertCliente() {
        if(flag==1){
           showAlarm("Error","No se puede ingresar un registro ya existente");
           return;
        }
        try {
            // Prepare the Cliente object
            Persona persona = new Persona(0, txtNom.getText(), txtApe.getText(), txtTelUsr.getText(),
                    selector.getSelectionModel().getSelectedItem().getIdCiudad());
            Usuario user = new Usuario(0, txtNomUsr.getText(), txtCont.getText(), 1);
            Cliente cliente = new Cliente(0, persona, user, (byte) 1);

            // Convert the Cliente object to JSON
            String clienteJson = gson.toJson(cliente);
            System.out.println(clienteJson);

            // Send the POST request using Unirest
            HttpResponse<String> response = Unirest.post("http://localhost:8080/Mexicode_Proyecto/api/cliente/insert")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("datosCliente", clienteJson) // Send the JSON as a form field
                    .asString();

            // Handle the response
            if (response.getStatus() == 200) {
                System.out.println("Cliente enviado exitosamente.");
                cancelarCl(); // Reset the form
                imprimirTablaCl(); // Refresh the table
            } else {
                System.err.println("Error al enviar cliente: " + response.getStatus() + " - " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCliente(){
        if(flag==0){
            showAlarm("Error","No ha seleccionado ningun registro.");
            return;
        }
        try {
            // Prepare the Cliente object
            Persona persona = new Persona(Integer.parseInt(txtIdPer.getText()), txtNom.getText(), txtApe.getText(), txtTelUsr.getText(),
                    selector.getSelectionModel().getSelectedItem().getIdCiudad());
            Usuario user = new Usuario(Integer.parseInt(txtIdUsr.getText()), txtNomUsr.getText(), txtCont.getText(), 1);
            Cliente cliente = new Cliente(Integer.parseInt(txtIdCl.getText()), persona, user, (byte) 1);

            // Convert the Cliente object to JSON
            String clienteJson = gson.toJson(cliente);
            System.out.println(clienteJson);

            // Send the POST request using Unirest
            HttpResponse<String> response = Unirest.post("http://localhost:8080/Mexicode_Proyecto/api/cliente/update")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("datosCliente", clienteJson) // Send the JSON as a form field
                    .asString();

            // Handle the response
            if (response.getStatus() == 200) {
                System.out.println("Cliente enviado exitosamente.");
                cancelarCl(); // Reset the form
                imprimirTablaCl(); // Refresh the table
                flag=0;
            } else {
                System.err.println("Error al enviar cliente: " + response.getStatus() + " - " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteCliente(){
        if(flag==0){
            showAlarm("Error","No ha seleccionado ningun registro.");
            return;
        }
        try {
            // Prepare the Cliente object
            Persona persona = new Persona(Integer.parseInt(txtIdPer.getText()), txtNom.getText(), txtApe.getText(), txtTelUsr.getText(),
                    selector.getSelectionModel().getSelectedItem().getIdCiudad());
            Usuario user = new Usuario(Integer.parseInt(txtIdUsr.getText()), txtNomUsr.getText(), txtCont.getText(), 1);
            Cliente cliente = new Cliente(Integer.parseInt(txtIdCl.getText()), persona, user, (byte) 1);

            // Convert the Cliente object to JSON
            String clienteJson = gson.toJson(cliente);
            System.out.println(clienteJson);

            // Send the POST request using Unirest
            HttpResponse<String> response = Unirest.post("http://localhost:8080/Mexicode_Proyecto/api/cliente/delete")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("datosCliente", clienteJson) // Send the JSON as a form field
                    .asString();

            // Handle the response
            if (response.getStatus() == 200) {
                System.out.println("Cliente enviado exitosamente.");
                cancelarCl(); // Reset the form
                imprimirTablaCl();
                flag=0;// Refresh the table
            } else {
                System.err.println("Error al enviar cliente: " + response.getStatus() + " - " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void cancelarCl() {
        txtIdCl.clear();
        txtNom.clear();
        txtApe.clear();
        txtTelUsr.clear();
        txtIdPer.clear();
        txtNomUsr.clear();
        txtCont.clear();
        txtIdUsr.clear();
        selector.setValue(null);
        flag=0;
    }

    private void loadCiudades() {
        new Thread(() ->{
            HttpResponse<String> response = Unirest.get("http://localhost:8080/Mexicode_Proyecto/api/cliente/getAllCiudades").asString();
            //System.out.println(response.getBody());
            Platform.runLater(() ->{
                //txtRespuesta.setText(response.getBody());
                Gson gson = new Gson();
                ciudades = FXCollections.observableArrayList(List.of(gson.fromJson(response.getBody(), Ciudad[].class)));
                selector.setItems(ciudades);
            });
        }).start();
    }
    private Ciudad findCiudadById(int id) {
        Ciudad ciudad = null;
        for (Ciudad item : ciudades) {
            if(item.getIdCiudad() == id) {
                return item;
            }
        }
        return null;
    }
    public static void showAlarm(String title, String message) {
        Platform.runLater(() -> { // Ensure it runs on the JavaFX Application Thread
            Alert alert = new Alert(Alert.AlertType.WARNING); // You can change the type to ERROR, INFORMATION, etc.
            alert.setTitle(title);
            alert.setHeaderText(null); // No header for simplicity
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

}
