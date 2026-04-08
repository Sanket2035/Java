package com.hdpack.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainLayoutController {

    @FXML private BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        showDashboard(); // Load dashboard by default
    }

    @FXML
    private void showDashboard() {
        loadView("Dashboard.fxml");
    }

    @FXML
    private void showInventory() {
        loadView("Inventory.fxml");
    }

    @FXML
    private void showJobs() {
        loadView("JobScheduling.fxml");
    }

    @FXML
    private void showBilling() {
         loadView("Billing.fxml");
    }

    private void loadView(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hdpack/client/views/" + fxmlFileName));
            Parent view = loader.load();
            mainBorderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}