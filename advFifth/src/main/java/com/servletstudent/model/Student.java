package com.servletstudent.model;

/**
 * Student Model Class
 *
 * Represents a student entity with basic information.
 * This is a Plain Old Java Object (POJO) used to transfer
 * data between the servlet and JSP views.
 *
 * @author Developer
 * @version 1.0
 */
public class Student {

    /** Unique identifier for the student */
    private int id;

    /** Student's full name */
    private String name;

    /** Student's email address */
    private String email;

    /** Student's password (stored as plain text for this demo) */
    private String password;

    /** Student's residential address */
    private String address;

    /**
     * Default constructor
     */
    public Student() {
    }

    /**
     * Parameterized constructor
     *
     * @param id Student ID
     * @param name Student name
     * @param email Student email
     * @param password Student password
     * @param address Student address
     */
    public Student(int id, String name, String email, String password, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    // ==================== Getters and Setters ====================

    /**
     * Gets the student ID
     * @return the student ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the student ID
     * @param id the student ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the student name
     * @return the student name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the student name
     * @param name the student name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the student email
     * @return the student email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the student email
     * @param email the student email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the student password
     * @return the student password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the student password
     * @param password the student password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the student address
     * @return the student address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the student address
     * @param address the student address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns a string representation of the student
     * @return string in format "Student{id, name, email, address}"
     */
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
