package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Replace placeholders with appropriate values from your environment
    private static final String DB_URL = "jdbc:mysql://localhost:3306/<lms>";
    private static final String DB_USERNAME = "<root>";
    private static final String DB_PASSWORD = "<12345>";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    /**
     * Handles GET requests.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve username and password from request parameters
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Register JDBC driver (assuming using MySQL Connector/J)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection using secure credentials
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","12345");

            // Create a prepared statement to prevent SQL injection
            stmt = conn.prepareStatement("SELECT * FROM employee WHERE username=? AND passcode=?");
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query and retrieve results
            rs = stmt.executeQuery();

            // Handle login success based on designation
            if (rs.next()) {
                String designation = rs.getString("designation");
                String id = rs.getString("Eid");

                HttpSession session = request.getSession();
                session.setAttribute("Eid", id);

                if (designation.equals("employee")) {
                    response.sendRedirect("EmployeeHomePage.html"); // Replace with employee homepage
                } else if (designation.equals("manager")) {
                    response.sendRedirect("AdminHomePage.html"); // Replace with manager homepage
                } else {
                    out.println("<h1>Invalid username or password. Please try again.</h1>");
                }
            } else {
                out.println("<h1>Invalid username or password. Please try again.</h1>");
            }
        } catch (SQLException se) {
            // Handle database errors gracefully, avoiding sensitive information
            out.println("<h2>An error occurred:</h2>");
            out.println("<pre>");
            se.printStackTrace(out);
            out.println("</pre>");
        } catch (Exception e) {
            // Handle other errors gracefully, avoiding sensitive information
            out.println("<h2>An error occurred:</h2>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        } finally {
            // Close resources in reverse order
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // Handle closing statement error gracefully
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    // Handle closing statement error gracefully
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // Handle closing connection error gracefully
                    e.printStackTrace();
                }
            }
        }

        out.close();
    }
}

  