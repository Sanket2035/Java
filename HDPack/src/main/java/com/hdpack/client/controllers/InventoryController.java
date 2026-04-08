package com.hdpack.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;

// Simple inner class for data model (mocking your Entity for UI testing)
import com.hdpack.client.models.InventoryItemViewModel;

public class InventoryController {

    @FXML private TableView<InventoryItemViewModel> inventoryTable;
    @FXML private TableColumn<InventoryItemViewModel, String> colName;
    @FXML private TableColumn<InventoryItemViewModel, String> colCategory;
    @FXML private TableColumn<InventoryItemViewModel, Integer> colQuantity;
    @FXML private TableColumn<InventoryItemViewModel, String> colUnit;
    @FXML private TableColumn<InventoryItemViewModel, Integer> colMinStock;
    @FXML private TableColumn<InventoryItemViewModel, String> colStatus;
    @FXML private TableColumn<InventoryItemViewModel, Void> colActions;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadMockData();
    }

    private void setupTableColumns() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colMinStock.setCellValueFactory(new PropertyValueFactory<>("minStock"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Custom Cell Factory for "Category" to look like a Pill
        colCategory.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Label label = new Label(item);
                    // Inline styling for the grey pill look seen in Figma
                    label.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #475569; -fx-padding: 4 12; -fx-background-radius: 12;");
                    setGraphic(label);
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });

        // Custom Cell Factory for "Status" to be Green
        colStatus.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    if ("In Stock".equals(item)) {
                        setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;"); // Green
                    } else {
                        setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;"); // Red for Low Stock
                    }
                }
            }
        });

        // Add Edit/Delete Buttons to Action Column
        addActionsButtons();
    }

    private void addActionsButtons() {
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("✎");
            private final Button deleteBtn = new Button("🗑");
            private final HBox pane = new HBox(10, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #3b82f6; -fx-cursor: hand;");
                deleteBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ef4444; -fx-cursor: hand;");
                pane.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }

    @FXML
    private void handleAddItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hdpack/client/views/AddInventoryModal.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Blocks interaction with main window
            stage.setTitle("Add Inventory Item");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // After modal closes, you would reload data here
            System.out.println("Modal closed, refreshing data...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMockData() {
        ObservableList<InventoryItemViewModel> data = FXCollections.observableArrayList(
                new InventoryItemViewModel("Glossy Sticker Paper A4", "Paper", 500, "sheets", 100, "In Stock"),
                new InventoryItemViewModel("Cyan Ink Cartridge", "Ink", 8, "cartridges", 5, "In Stock"),
                new InventoryItemViewModel("Vinyl Sticker Roll", "Paper", 2, "rolls", 10, "Low Stock")
        );
        inventoryTable.setItems(data);
    }
}