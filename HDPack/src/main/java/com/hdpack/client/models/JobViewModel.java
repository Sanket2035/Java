package com.hdpack.client.models;

import javafx.beans.property.*;
import java.time.LocalDate;

public class JobViewModel {
    private final StringProperty jobId = new SimpleStringProperty();
    private final StringProperty brand = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> deadline = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty assignedTo = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();

    public JobViewModel(String id, String brand, int qty, LocalDate start, LocalDate end, String status, String assigned, String desc) {
        this.jobId.set(id);
        this.brand.set(brand);
        this.quantity.set(qty);
        this.startDate.set(start);
        this.deadline.set(end);
        this.status.set(status);
        this.assignedTo.set(assigned);
        this.description.set(desc);
    }

    // Getters
    public String getJobId() { return jobId.get(); }
    public String getBrand() { return brand.get(); }
    public int getQuantity() { return quantity.get(); }
    public LocalDate getStartDate() { return startDate.get(); }
    public LocalDate getDeadline() { return deadline.get(); }
    public String getStatus() { return status.get(); }
    public String getAssignedTo() { return assignedTo.get(); }
    public String getDescription() { return description.get(); }
}