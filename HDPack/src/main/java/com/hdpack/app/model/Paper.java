package com.hdpack.app.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Document(collection = "inventory_paper")
public class Paper extends BaseEntity {
	private String brand;
	
	@Indexed
	private String type;
	private double width;
	
	@Min(1)
	private int rollSheets;
}