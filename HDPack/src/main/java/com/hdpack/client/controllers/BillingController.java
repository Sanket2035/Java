package com.hdpack.client.controllers;

import com.hdpack.client.models.JobViewModel;
import com.hdpack.service.JobCardService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

import java.io.File;
import java.time.LocalDate;

public class BillingController {

    @FXML private FlowPane billingContainer;

    // In a real app, this list comes from the API (filtering for status="Completed")
    private ObservableList<JobViewModel> completedJobs = FXCollections.observableArrayList();
    private final JobCardService pdfService = new JobCardService();

    @FXML
    public void initialize() {
        loadMockCompletedJobs();
        renderCards();
    }

    private void renderCards() {
        billingContainer.getChildren().clear();
        for (JobViewModel job : completedJobs) {
            billingContainer.getChildren().add(createBillingCard(job));
        }
    }

    private VBox createBillingCard(JobViewModel job) {
        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        card.setPrefWidth(450);

        // Header: Icon + Name + Status
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("\uD83D\uDCC4"); // Page icon unicode
        icon.setStyle("-fx-font-size: 24px; -fx-background-color: #dcfce7; -fx-padding: 5 10; -fx-background-radius: 8;");

        VBox titleBox = new VBox(2);
        Label brand = new Label(job.getBrand());
        brand.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label jobId = new Label(job.getJobId());
        jobId.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px;");
        titleBox.getChildren().addAll(brand, jobId);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label status = new Label("Completed");
        status.getStyleClass().add("status-pill-green");

        header.getChildren().addAll(icon, titleBox, spacer, status);

        // Details
        VBox details = new VBox(8);
        details.getChildren().add(createDetailRow("Job ID:", job.getJobId()));
        details.getChildren().add(createDetailRow("Quantity:", job.getQuantity() + " units"));
        details.getChildren().add(createDetailRow("Completed Date:", LocalDate.now().toString())); // Mock date
        // Note: For a real bill, you'd add Price here: e.g., $500.00

        // Action Button
        Button generatePdfBtn = new Button("⬇ Generate PDF Job Card");
        generatePdfBtn.getStyleClass().add("btn-primary");
        generatePdfBtn.setMaxWidth(Double.MAX_VALUE);
        generatePdfBtn.setOnAction(e -> generatePdf(job));

        card.getChildren().addAll(header, details, generatePdfBtn);
        return card;
    }

    private HBox createDetailRow(String label, String value) {
        HBox row = new HBox();
        Label l = new Label(label);
        l.setPrefWidth(120);
        l.setStyle("-fx-text-fill: #64748b;");
        Label v = new Label(value);
        v.setStyle("-fx-font-weight: bold;");
        row.getChildren().addAll(l, v);
        return row;
    }

    private void generatePdf(JobViewModel job) {
        try {
            String home = System.getProperty("user.home");
            String path = home + File.separator + "Documents" + File.separator + "JobCard_" + job.getJobId() + ".pdf";

            pdfService.generateJobCard(job, path);

            // Open the file immediately
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(new File(path));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // In a real app, show an Alert dialog here
        }
    }

    private void loadMockCompletedJobs() {
        completedJobs.add(new JobViewModel("INV-003", "Organic Foods Co", 10000, LocalDate.now(), LocalDate.now(), "Completed", "Machine 1", "Product Labels"));
        completedJobs.add(new JobViewModel("INV-004", "Tesla Motors", 500, LocalDate.now(), LocalDate.now(), "Completed", "Machine 2", "Battery Warning Labels"));
    }
}