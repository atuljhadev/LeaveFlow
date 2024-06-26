package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ApproveRejectServlet")
public class ApproveRejectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection connection = null;

        String jdbcUrl = "jdbc:mysql://localhost:3306/lms";
        String dbUser = "root";
        String dbPassword = "12345";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            String employeeId = request.getParameter("eid");
            String action = request.getParameter("action");

            String sqlQuery = "UPDATE lms_leavemanagement SET status=? WHERE Eid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, action);
            preparedStatement.setString(2, employeeId);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                out.println("<p>Successfully updated leave request status.</p>");
            } else {
                out.println("<p>Error updating leave request status. No matching record found.</p>");
            }
        } catch (SQLException e) {
            out.println("<p>Error accessing database: " + e.getMessage() + "</p>");
        } catch (ClassNotFoundException e) {
            out.println("<p>Error: JDBC driver not found. Please ensure the driver is included in your classpath.</p>");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    out.println("<p>Error closing connection: " + e.getMessage() + "</p>");
                }
            }
            out.close();
        }
    }
}
