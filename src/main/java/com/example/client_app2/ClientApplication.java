package com.example.client_app2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class ClientApplication extends Application {
    private Socket socket;
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Banking App");

        // Create the grid pane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Create the node host label and text field
        Label hostLabel = new Label("Node Host:");
        grid.add(hostLabel, 0, 0);
        TextField hostTextField = new TextField();
        grid.add(hostTextField, 1, 0);
        hostTextField.setText("localhost");
        // Create the node port label and text field
        Label portLabel = new Label("Node Port:");
        grid.add(portLabel, 0, 1);
        TextField portTextField = new TextField();
        portTextField.setText("5001");
        TextFormatter<String> textFormatter = new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d*")) ? change : null);
        portTextField.setTextFormatter(textFormatter);
        grid.add(portTextField, 1, 1);

        // Create the connect button
        Button connectButton = new Button("Connect");
        connectButton.setOnAction(e -> {
            String host = hostTextField.getText();
            int port = Integer.parseInt(portTextField.getText());
            connectToServerSocket(host,port,primaryStage);
        });
        HBox connectBox = new HBox(10);
        connectBox.setAlignment(Pos.BOTTOM_RIGHT);
        connectBox.getChildren().add(connectButton);
        grid.add(connectBox, 1, 2);

        // Create the scene and show the stage
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void connectToServerSocket(String host, int port, Stage stage) {
        try {
            // Connect to server socket
            socket = new Socket(host, port);

            // Display success message on GUI
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Connection Successful");
                alert.setHeaderText("Connected to server socket");
                alert.showAndWait();
                stage.close();
            });
            loginForm();
        } catch (IOException e) {
            // Display error message on GUI
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setHeaderText("Failed to connect to server socket");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            });
        }
    }
    private void loginForm() {
        // Create UI elements
        Stage loginForm = new Stage();
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button submitButton = new Button("Login");
        //submitButton.setOnAction(event -> sendCredentialsToServer(new Credentials(usernameField.getText(), passwordField.getText()),accountCreationForm));

        // Set up grid pane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(5);
        grid.setHgap(5);
        GridPane.setConstraints(usernameLabel, 0, 0);
        GridPane.setConstraints(usernameField, 1, 0);
        GridPane.setConstraints(passwordLabel, 0, 1);
        GridPane.setConstraints(passwordField, 1, 1);
        GridPane.setConstraints(submitButton, 1, 2);
        grid.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, submitButton);

        // Create scene and show it
        loginForm.setTitle("Login Form");
        Scene scene = new Scene(grid, 300, 150);
        loginForm.setScene(scene);
        loginForm.show();
    }

    public static void main(String[] args) {
        launch();
    }
}