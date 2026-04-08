# Student Management System

A web-based student management system built with **Java Servlets** and **JSP** demonstrating traditional MVC architecture without using any frameworks like Spring MVC.

## Project Overview

This mini project implements a complete CRUD (Create, Read, Update, Delete) application for managing student records. It uses:

- **Java Servlets** for controller logic
- **JSP with JSTL** for view layer
- **JDBC** for database operations
- **PostgreSQL** as the database
- **web.xml** for servlet configuration (not annotations)

## Features

- **Register Students**: Add new student records with name, email, password, and address
- **View Students**: Display all registered students in a tabular format
- **Edit Students**: Update existing student information
- **Delete Students**: Remove student records from the database
- **Search Functionality**: Search students by name (bonus feature)

## Technology Stack

| Component | Technology |
|-----------|------------|
| **Frontend** | JSP, JSTL, CSS3 |
| **Backend** | Java Servlets (Java EE) |
| **Database** | PostgreSQL |
| **Build Tool** | Maven |
| **Server** | Apache Tomcat 8+ |

## Project Structure

```
advFifth/
├── src/
│   └── main/
│       ├── java/com/servletstudent/
│       │   ├── controller/
│       │   │   └── StudentServlet.java      # Main controller servlet
│       │   ├── dao/
│       │   │   └── StudentDAO.java          # Data Access Object
│       │   ├── model/
│       │   │   └── Student.java             # Student entity class
│       │   └── util/
│       │       └── DatabaseConnection.java  # Database utility
│       └── webapp/
│           ├── WEB-INF/
│           │   ├── pages/
│           │   │   ├── index.jsp            # Home/registration form
│           │   │   ├── viewStudents.jsp     # Display all students
│           │   │   ├── editForm.jsp         # Edit student form
│           │   │   └── error.jsp            # Error page
│           │   └── web.xml                  # Servlet configuration
│           ├── css/
│           │   └── style.css                # Application styles
│           └── index.jsp                    # Landing page (redirect)
└── pom.xml                                  # Maven dependencies
```

## Database Setup

### Prerequisites

1. Install PostgreSQL
2. Create a database named `spring`
3. Update connection settings in `DatabaseConnection.java` if needed

### Table Schema

```sql
CREATE TABLE student_model (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    pass VARCHAR(100) NOT NULL,
    address VARCHAR(255)
);
```

### Sample Data

```sql
INSERT INTO student_model (name, email, pass, address) VALUES
    ('John Doe', 'john@example.com', 'password123', '123 Main St, City'),
    ('Jane Smith', 'jane@example.com', 'password456', '456 Oak Ave, Town');
```

## Configuration

### Database Connection

Edit `src/main/java/com/servletstudent/util/DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:postgresql://localhost:5432/spring";
private static final String DB_USER = "postgres";
private static final String DB_PASSWORD = "your_password";
```

### Server Configuration

The application is configured to run on Tomcat. Update `pom.xml` if you need different settings:

```xml
<plugin>
    <groupId>org.apache.tomcat.maven</groupId>
    <artifactId>tomcat7-maven-plugin</artifactId>
    <version>2.2</version>
    <configuration>
        <port>8080</port>
        <path>/student-management</path>
    </configuration>
</plugin>
```

## Building and Running

### Option 1: Using Maven and Tomcat Plugin

```bash
# Navigate to project directory
cd advFifth

# Run with Tomcat plugin
mvn tomcat7:run

# Access application at: http://localhost:8080/student-management
```

### Option 2: Build WAR and Deploy to Tomcat

```bash
# Build the WAR file
mvn clean package

# Copy WAR to Tomcat webapps directory
cp target/student-management-system.war $TOMCAT_HOME/webapps/

# Start Tomcat
$TOMCAT_HOME/bin/startup.sh  # Linux/Mac
$TOMCAT_HOME/bin/startup.bat # Windows

# Access application at: http://localhost:8080/student-management-system
```

### Option 3: Using Eclipse/IDE

1. Import the project as a Maven project
2. Right-click project → Run As → Run on Server
3. Select Apache Tomcat
4. Application will open in browser

## URL Mappings

| URL | Method | Action | Description |
|-----|--------|--------|-------------|
| `/student?action=view` | GET | view | Display all students |
| `/student?action=add` | GET | showAddForm | Show registration form |
| `/student?action=add` | POST | addStudent | Add new student |
| `/student?action=edit&id=1` | GET | showEditForm | Show edit form for student |
| `/student?action=update` | POST | updateStudent | Update student info |
| `/student?action=delete&id=1` | GET | deleteStudent | Delete student by ID |

## Servlet Configuration (web.xml)

The application uses `web.xml` for servlet configuration instead of annotations:

```xml
<servlet>
    <servlet-name>StudentServlet</servlet-name>
    <servlet-class>com.servletstudent.controller.StudentServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>StudentServlet</servlet-name>
    <url-pattern>/student</url-pattern>
</servlet-mapping>
```

## API Documentation

### StudentServlet Methods

- `doGet(HttpServletRequest, HttpServletResponse)` - Handles GET requests
- `doPost(HttpServletRequest, HttpServletResponse)` - Handles POST requests
- `viewAllStudents()` - Retrieves and displays all students
- `addStudent()` - Processes new student registration
- `updateStudent()` - Updates existing student data
- `deleteStudent()` - Removes a student record

### StudentDAO Methods

- `addStudent(Student)` - Insert new student
- `getAllStudents()` - Get all students
- `getStudentById(int)` - Get student by ID
- `updateStudent(Student)` - Update student
- `deleteStudent(int)` - Delete student
- `searchStudentsByName(String)` - Search by name

## Screenshots

### Home Page (Registration Form)
The landing page contains the student registration form with fields for name, email, password, and address.

### View Students Page
Displays all registered students in a table with Edit and Delete action buttons.

### Edit Form
Pre-populated form for updating student information.

## Security Notes

> **Warning**: This is a demonstration project. For production use:

1. **Password Encryption**: Currently passwords are stored in plain text. Use BCrypt or similar hashing.
2. **SQL Injection**: Using PreparedStatement prevents SQL injection.
3. **XSS Protection**: Add input sanitization and output encoding.
4. **CSRF Protection**: Implement CSRF tokens for form submissions.
5. **Authentication**: Add login/authentication system.
6. **Input Validation**: Add server-side validation for all inputs.

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify PostgreSQL is running
   - Check database credentials in `DatabaseConnection.java`
   - Ensure database `spring` exists

2. **404 Error**
   - Verify Tomcat is running on correct port
   - Check context path in URL
   - Ensure WAR is deployed correctly

3. **ClassNotFoundException**
   - Run `mvn clean install` to download dependencies
   - Check PostgreSQL driver is in classpath

4. **JSTL Not Working**
   - Ensure JSTL dependency is in `pom.xml`
   - Add JSTL JAR to `WEB-INF/lib` if deploying manually

## Future Enhancements

- [ ] User authentication and authorization
- [ ] Password encryption using BCrypt
- [ ] Student profile pictures upload
- [ ] Export to Excel/PDF
- [ ] Pagination for student list
- [ ] AJAX-based operations
- [ ] REST API endpoints
- [ ] Unit tests with JUnit

## License

This project is created for educational purposes. Feel free to use and modify as needed.

## Author

**Developer**

- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com

## Acknowledgments

- Java Servlet Specification (Oracle/Oracle)
- Apache Tomcat Project
- PostgreSQL Community
- Maven Project

---

**Built with Java Servlets & JSP** | **Version 1.0.0** | **2026**
