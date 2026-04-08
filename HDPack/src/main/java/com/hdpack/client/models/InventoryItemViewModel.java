package com.hdpack.client.models;

import javafx.beans.property.*;

public class InventoryItemViewModel {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final StringProperty unit = new SimpleStringProperty();
    private final IntegerProperty minStock = new SimpleIntegerProperty();
    private final StringProperty status = new SimpleStringProperty();

    public InventoryItemViewModel(String name, String cat, int qty, String unit, int min, String status) {
        this.name.set(name);
        this.category.set(cat);
        this.quantity.set(qty);
        this.unit.set(unit);
        this.minStock.set(min);
        this.status.set(status);
    }

    // Getters for properties (required for PropertyValueFactory)
    public StringProperty nameProperty() { return name; }
    public StringProperty categoryProperty() { return category; }
    public IntegerProperty quantityProperty() { return quantity; }
    public StringProperty unitProperty() { return unit; }
    public IntegerProperty minStockProperty() { return minStock; }
    public StringProperty statusProperty() { return status; }
}