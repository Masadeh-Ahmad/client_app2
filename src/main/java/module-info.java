module com.example.client_app2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.client_app2 to javafx.fxml;
    exports com.example.client_app2;
}