package com.hdpack.app.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "inventory_punch")
public class Punch extends BaseEntity {

    private String serialNumber;
    @Indexed
    private String type;
    private double height;
    private double width;
    private double cavitiesAcross;
    private double cavitiesAround;
}