package com.hdpack.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddInventoryController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private TextField qtyField;
    @FXML private TextField unitField;
    @FXML private TextField minStockField;

    @FXML
    public void initialize() {
        // Initialize Combo Data
        categoryCombo.getItems().addAll("Paper", "Ink", "Punch", "Paper rolls", "Adhesive");
    }

    @FXML
    private void saveItem() {
        // In a real app, you would validate inputs and call your Spring Boot API here
        System.out.println("Saving: " + nameField.getText());
        closeModal();
    }

    @FXML
    private void closeModal() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}