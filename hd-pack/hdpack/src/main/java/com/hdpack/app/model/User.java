package com.hdpack.app.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hdpack.app.model.enums.Role;

import lombok.Data;

@Data
@Document(collection = "users")
public class User extends BaseEntity {

    @Indexed(unique = true)
    private String username;

    private String password;

    private String fullName;

    private Role role;
    
    private String department;
}