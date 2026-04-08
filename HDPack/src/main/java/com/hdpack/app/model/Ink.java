package com.hdpack.app.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Document(collection = "inventory_ink")
public class Ink extends BaseEntity {

    private String colorName;

    private String colorCode;

    private String brand;

    @Min(1)
    private double stockLtr;
}