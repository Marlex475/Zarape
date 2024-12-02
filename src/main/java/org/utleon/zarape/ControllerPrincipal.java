package org.utleon.zarape;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ControllerPrincipal {
    @FXML
    private HBox clientesItemBar;

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private HBox homeItemBar;
    @FXML
    private HBox sucursalItemBar;
    @FXML
    private HBox salirItemBar;

    @FXML
    public void initialize() {
        homeItemBar.setOnMouseClicked(event -> {
            loadFXML("home");
        });
        sucursalItemBar.setOnMouseClicked(event -> {
            loadFXML("sucursales");
        });
        salirItemBar.setOnMouseClicked(event -> {
            System.exit(0);
        });
        clientesItemBar.setOnMouseClicked(event ->{
            loadFXML("clientes");
        });
    }

    private void loadFXML(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utleon/zarape/" + fxml + ".fxml"));
            AnchorPane newLoadedPane = loader.load();
            AnchorPane.setTopAnchor(newLoadedPane, 0.0);
            AnchorPane.setRightAnchor(newLoadedPane, 0.0);
            AnchorPane.setBottomAnchor(newLoadedPane, 0.0);
            AnchorPane.setLeftAnchor(newLoadedPane, 0.0);
            mainContainer.getChildren().clear(); // Limpia el contenedor
            mainContainer.getChildren().add(newLoadedPane); // Añade el nuevo FXML cargado
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

