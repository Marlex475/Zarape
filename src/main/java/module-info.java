module org.utleon.zarape {
    requires javafx.controls;
    requires javafx.fxml;
    requires unirest.java;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpclient;


    opens org.utleon.zarape.model to com.google.gson;
    opens org.utleon.zarape to javafx.fxml;
    exports org.utleon.zarape;

}