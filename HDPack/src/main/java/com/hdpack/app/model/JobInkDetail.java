package com.hdpack.app.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class JobInkDetail {

    @DBRef 
    private Ink ink; 

    private double consumedLtr;
}