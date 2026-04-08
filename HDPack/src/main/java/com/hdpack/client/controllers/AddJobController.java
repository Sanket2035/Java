package com.hdpack.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddJobController {

    @FXML private TextField brandField;
    @FXML private TextField qtyField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker deadlinePicker;
    @FXML private ComboBox<String> statusCombo;
    @FXML private TextField assignedToField;
    @FXML private TextArea notesArea;

    @FXML
    public void initialize() {
        statusCombo.getItems().addAll("Pending", "In-progress", "Completed");
        statusCombo.getSelectionModel().select("Pending"); // Default
    }

    @FXML
    private void saveJob() {
        // Collect data
        String brand = brandField.getText();
        String qty = qtyField.getText();
        // ... (validate and send to Spring Boot API) ...

        System.out.println("Creating Job for: " + brand);
        closeModal();
    }

    @FXML
    private void closeModal() {
        Stage stage = (Stage) brandField.getScene().getWindow();
        stage.close();
    }
}