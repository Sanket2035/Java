package com.servletstudent.controller;

import com.servletstudent.dao.StudentDAO;
import com.servletstudent.model.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Student Servlet Controller
 *
 * This servlet handles all HTTP requests related to student management.
 * It acts as the controller in the MVC (Model-View-Controller) pattern,
 * processing requests, interacting with the DAO layer, and forwarding
 * to appropriate JSP views.
 *
 * <h3>URL Mappings:</h3>
 * <ul>
 *     <li>{@code /add} - POST: Add a new student</li>
 *     <li>{@code /view} - GET: Display all students</li>
 *     <li>{@code /edit} - GET: Show edit form for a student</li>
 *     <li>{@code /update} - POST: Update student information</li>
 *     <li>{@code /delete} - GET: Delete a student by ID</li>
 * </ul>
 *
 * <h3>Request Flow:</h3>
 * <ol>
 *     <li>Client sends HTTP request to servlet URL</li>
 *     <li>Servlet processes request based on action parameter</li>
 *     <li>Servlet calls appropriate DAO method</li>
 *     <li>Servlet forwards request to JSP view with results</li>
 * </ol>
 *
 * @author Developer
 * @version 1.0
 * @see StudentDAO
 * @see Student
 */
public class StudentServlet extends HttpServlet {

    /**
     * Data Access Object for student operations
     * Initialized once when servlet is loaded
     */
    private StudentDAO studentDAO;

    /**
     * Initializes the servlet and creates the DAO instance.
     * Called once when the servlet is first loaded by the container.
     *
     * @throws ServletException if servlet initialization fails
     */
    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
        System.out.println("StudentServlet initialized successfully.");
    }

    /**
     * Handles GET requests.
     * Routes to specific handlers based on the action parameter.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException if servlet encounters difficulties
     * @throws IOException      if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        // Default action is to view all students
        if (action == null || action.isEmpty()) {
            action = "view";
        }

        System.out.println("GET Request received with action: " + action);

        switch (action) {
            case "view":
                viewAllStudents(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteStudent(req, resp);
                break;
            case "add":
                showAddForm(req, resp);
                break;
            default:
                viewAllStudents(req, resp);
                break;
        }
    }

    /**
     * Handles POST requests.
     * Routes to specific handlers based on the action parameter.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException if servlet encounters difficulties
     * @throws IOException      if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        System.out.println("POST Request received with action: " + action);

        switch (action) {
            case "add":
                addStudent(req, resp);
                break;
            case "update":
                updateStudent(req, resp);
                break;
            default:
                resp.sendRedirect("index.jsp");
                break;
        }
    }

    /**
     * Displays the student registration form.
     * Forwards to index.jsp which contains the add student form.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException if servlet encounters difficulties
     * @throws IOException      if an input/output error occurs
     */
    private void showAddForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("Showing add student form.");
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }

    /**
     * Retrieves all students from the database and forwards to view page.
     * Sets the student list as a request attribute for the JSP to display.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException if servlet encounters difficulties
     * @throws IOException      if an input/output error occurs
     */
    private void viewAllStudents(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("Fetching all students...");
        List<Student> students = studentDAO.getAllStudents();
        req.setAttribute("studentList", students);
        req.setAttribute("count", students.size());

        System.out.println("Forwarding to viewStudents.jsp with " + students.size() + " students.");
        req.getRequestDispatcher("/WEB-INF/pages/viewStudents.jsp").forward(req, resp);
    }

    /**
     * Shows the edit form pre-populated with existing student data.
     * Retrieves the student by ID from the request parameter.
     *
     * @param req  HttpServletRequest object containing student ID
     * @param resp HttpServletResponse object
     * @throws ServletException if servlet encounters difficulties
     * @throws IOException      if an input/output error occurs
     */
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        System.out.println("Edit request for student ID: " + idParam);

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Student student = studentDAO.getStudentById(id);

                if (student != null) {
                    req.setAttribute("student", student);
                    System.out.println("Forwarding to editForm.jsp for student: " + student.getName());
                    req.getRequestDispatcher("/WEB-INF/pages/editForm.jsp").forward(req, resp);
                } else {
                    System.out.println("Student not found with ID: " + id);
                    req.setAttribute("message", "Student not found!");
                    req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid student ID format: " + idParam);
                req.setAttribute("message", "Invalid student ID!");
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            }
        } else {
            System.out.println("No student ID provided for edit.");
            resp.sendRedirect("index.jsp");
        }
    }

    /**
     * Adds a new student to the database.
     * Extracts student data from request parameters and creates a new Student object.
     *
     * @param req  HttpServletRequest object containing student form data
     * @param resp HttpServletResponse object
     * @throws ServletException if servlet encounters difficulties
     * @throws IOException      if an input/output error occurs
     */
    private void addStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("Processing new student registration...");

        // Extract form data from request
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String address = req.getParameter("address");

        // Validate required fields
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            req.setAttribute("message", "Name and Email are required fields!");
            req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
            return;
        }

        // Create student object and add to database
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setPassword(password != null ? password : "");
        student.setAddress(address != null ? address : "");

        boolean success = studentDAO.addStudent(student);

        if (success) {
            System.out.println("Student added successfully: " + name);
            resp.sendRedirect("index.jsp?message=added");
        } else {
            System.out.println("Failed to add student: " + name);
            req.setAttribute("message", "Failed to add student. Please try again.");
            req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
        }
    }

    /**
     * Updates an existing student's information in the database.
     * Extracts updated data from request parameters.
     *
     * @param req  HttpServletRequest object containing updated student data
     * @param resp HttpServletResponse object
     * @throws ServletException if servlet encounters difficulties
     * @throws IOException      if an input/output error occurs
     */
    private void updateStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("Processing student update...");

        // Extract form data from request
        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String address = req.getParameter("address");

        if (idParam == null || idParam.trim().isEmpty()) {
            req.setAttribute("message", "Invalid student ID!");
            req.getRequestDispatcher("/WEB-INF/pages/editForm.jsp").forward(req, resp);
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            // Create student object with updated data
            Student student = new Student();
            student.setId(id);
            student.setName(name);
            student.setEmail(email);
            student.setPassword(password != null ? password : "");
            student.setAddress(address != null ? address : "");

            boolean success = studentDAO.updateStudent(student);

            if (success) {
                System.out.println("Student updated successfully: ID=" + id);
                resp.sendRedirect("index.jsp?message=updated");
            } else {
                System.out.println("Failed to update student: ID=" + id);
                req.setAttribute("message", "Failed to update student. Please try again.");
                req.getRequestDispatcher("/WEB-INF/pages/editForm.jsp").forward(req, resp);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid student ID format: " + idParam);
            req.setAttribute("message", "Invalid student ID!");
            req.getRequestDispatcher("/WEB-INF/pages/editForm.jsp").forward(req, resp);
        }
    }

    /**
     * Deletes a student from the database by ID.
     * After deletion, redirects to the view all students page.
     *
     * @param req  HttpServletRequest object containing student ID to delete
     * @param resp HttpServletResponse object
     * @throws ServletException if servlet encounters difficulties
     * @throws IOException      if an input/output error occurs
     */
    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        System.out.println("Delete request for student ID: " + idParam);

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                boolean success = studentDAO.deleteStudent(id);

                if (success) {
                    System.out.println("Student deleted successfully: ID=" + id);
                } else {
                    System.out.println("Failed to delete student: ID=" + id);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid student ID format: " + idParam);
            }
        }

        // Redirect to view all students page
        resp.sendRedirect("index.jsp?message=deleted");
    }

    /**
     * Cleans up resources when servlet is destroyed.
     * Called when the servlet is being taken out of service.
     */
    @Override
    public void destroy() {
        studentDAO = null;
        System.out.println("StudentServlet destroyed.");
    }
}
