module com.example.bell_catillon {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;


    opens com.example.bell_bouton to javafx.fxml;
    exports com.example.bell_bouton;
}