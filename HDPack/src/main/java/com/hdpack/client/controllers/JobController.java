package com.hdpack.client.controllers;

import com.hdpack.client.models.JobViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class JobController {

    @FXML private FlowPane jobsContainer; // Matches the FlowPane in your JobScheduling.fxml

    private ObservableList<JobViewModel> jobList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // 1. Load Mock Data (Later this comes from Spring Boot)
        loadMockData();

        // 2. Render Cards
        renderJobCards();
    }

    private void renderJobCards() {
        jobsContainer.getChildren().clear(); // Clear existing

        for (JobViewModel job : jobList) {
            jobsContainer.getChildren().add(createJobCard(job));
        }
    }

    private VBox createJobCard(JobViewModel job) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2); -fx-padding: 20;");
        card.setPrefWidth(400);

        // Header: Brand + Status
        HBox header = new HBox();
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        VBox titleBox = new VBox();
        Label brandLabel = new Label(job.getBrand());
        brandLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label idLabel = new Label("Job ID: " + job.getJobId());
        idLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
        titleBox.getChildren().addAll(brandLabel, idLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label statusPill = new Label(job.getStatus());
        String statusStyle = switch (job.getStatus()) {
            case "In-progress" -> "-fx-background-color: #dbeafe; -fx-text-fill: #1e40af;"; // Blue
            case "Pending" -> "-fx-background-color: #fef9c3; -fx-text-fill: #854d0e;";    // Yellow
            case "Completed" -> "-fx-background-color: #dcfce7; -fx-text-fill: #166534;";  // Green
            default -> "-fx-background-color: #f1f5f9; -fx-text-fill: #475569;";
        };
        statusPill.setStyle(statusStyle + " -fx-background-radius: 12; -fx-padding: 4 12; -fx-font-size: 12px;");

        header.getChildren().addAll(titleBox, spacer, statusPill);

        // Details Rows
        HBox qtyRow = createDetailRow("Quantity:", job.getQuantity() + " stickers");
        HBox startRow = createDetailRow("Start Date:", job.getStartDate().toString());
        HBox endRow = createDetailRow("Deadline:", job.getDeadline().toString());
        HBox assignRow = createDetailRow("Assigned To:", job.getAssignedTo());

        // Description Box
        Label descLabel = new Label(job.getDescription());
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 10; -fx-background-radius: 4; -fx-text-fill: #475569;");
        descLabel.setMaxWidth(Double.MAX_VALUE);

        // Buttons
        HBox actions = new HBox(10);
        actions.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        Button editBtn = new Button("Edit");
        editBtn.getStyleClass().add("btn-secondary");
        editBtn.setPrefWidth(80);

        Button deleteBtn = new Button("Delete");
        deleteBtn.getStyleClass().add("btn-danger-outline");
        deleteBtn.setPrefWidth(80);

        actions.getChildren().addAll(editBtn, deleteBtn);

        card.getChildren().addAll(header, qtyRow, startRow, endRow, assignRow, descLabel, actions);
        return card;
    }

    private HBox createDetailRow(String label, String value) {
        HBox row = new HBox(20);
        Label l = new Label(label);
        l.setPrefWidth(80);
        l.setStyle("-fx-text-fill: #64748b;");
        Label v = new Label(value);
        v.setStyle("-fx-font-weight: bold;");
        row.getChildren().addAll(l, v);
        return row;
    }

    @FXML
    public void openAddJobModal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hdpack/client/views/AddJobModal.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add New Job");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Reload data after modal closes
            System.out.println("Refreshing jobs...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMockData() {
        jobList.add(new JobViewModel("JOB-001", "Nike Sports", 5000, LocalDate.of(2025, 11, 20), LocalDate.of(2025, 11, 30), "In-progress", "Press Machine 1", "Custom die-cut logo stickers, glossy finish"));
        jobList.add(new JobViewModel("JOB-002", "Tech Startups Inc", 3000, LocalDate.of(2025, 11, 22), LocalDate.of(2025, 12, 5), "Pending", "Press Machine 2", "Holographic company logo stickers"));
    }
}