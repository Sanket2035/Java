package com.servletstudent.dao;

import com.servletstudent.model.Student;
import com.servletstudent.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Student Data Access Object (DAO)
 *
 * This class provides methods for performing CRUD (Create, Read, Update, Delete)
 * operations on the student_model table in the database.
 *
 * <p>All database operations use PreparedStatement to prevent SQL injection attacks.</p>
 *
 * <p>Database Operations:</p>
 * <ul>
 *     <li>{@link #addStudent(Student)} - Insert a new student</li>
 *     <li>{@link #getAllStudents()} - Retrieve all students</li>
 *     <li>{@link #getStudentById(int)} - Get a specific student by ID</li>
 *     <li>{@link #updateStudent(Student)} - Update student information</li>
 *     <li>{@link #deleteStudent(int)} - Delete a student by ID</li>
 * </ul>
 *
 * @author Developer
 * @version 1.0
 * @see Student
 * @see DatabaseConnection
 */
public class StudentDAO {

    /**
     * Adds a new student to the database.
     *
     * @param student The Student object containing student information
     * @return true if the student was added successfully, false otherwise
     *
     * SQL: INSERT INTO student_model (name, email, pass, address) VALUES (?, ?, ?, ?)
     */
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO student_model (name, email, pass, address) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getPassword());
            pstmt.setString(4, student.getAddress());

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Student added: " + student.getName() + ", Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all students from the database.
     *
     * @return List of all Student objects, or empty list if no students found
     *
     * SQL: SELECT * FROM student_model ORDER BY id DESC
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student_model ORDER BY id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setPassword(rs.getString("pass"));
                student.setAddress(rs.getString("address"));
                students.add(student);
            }

            System.out.println("Retrieved " + students.size() + " students from database.");

        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }

    /**
     * Retrieves a student by their unique ID.
     *
     * @param id The unique identifier of the student
     * @return Student object if found, null otherwise
     *
     * SQL: SELECT * FROM student_model WHERE id = ?
     */
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM student_model WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setPassword(rs.getString("pass"));
                    student.setAddress(rs.getString("address"));
                    System.out.println("Student found: " + student.getName());
                    return student;
                }
            }

            System.out.println("No student found with ID: " + id);

        } catch (SQLException e) {
            System.err.println("Error retrieving student by ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Updates an existing student's information in the database.
     *
     * @param student The Student object containing updated information
     *                The student must have a valid ID
     * @return true if the update was successful, false otherwise
     *
     * SQL: UPDATE student_model SET name=?, email=?, pass=?, address=? WHERE id=?
     */
    public boolean updateStudent(Student student) {
        String sql = "UPDATE student_model SET name = ?, email = ?, pass = ?, address = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getPassword());
            pstmt.setString(4, student.getAddress());
            pstmt.setInt(5, student.getId());

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Student updated: ID=" + student.getId() + ", Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a student from the database by their ID.
     *
     * @param id The unique identifier of the student to delete
     * @return true if the deletion was successful, false otherwise
     *
     * SQL: DELETE FROM student_model WHERE id = ?
     */
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM student_model WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Student deleted: ID=" + id + ", Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Searches for students by name (partial match).
     *
     * @param searchTerm The search term to match against student names
     * @return List of Student objects matching the search term
     *
     * SQL: SELECT * FROM student_model WHERE name LIKE ?
     */
    public List<Student> searchStudentsByName(String searchTerm) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student_model WHERE name LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchTerm + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setPassword(rs.getString("pass"));
                    student.setAddress(rs.getString("address"));
                    students.add(student);
                }
            }

            System.out.println("Search found " + students.size() + " students matching: " + searchTerm);

        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }
}
