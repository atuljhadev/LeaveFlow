package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ManagerHistory")
public class ManagerHistory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection connection = null; // Declare connection outside try block

        // Consider using environment variables or a secure configuration file for credentials
        String jdbcUrl = "jdbc:mysql://localhost:3306/lms";
        String dbUser = "root";
        String dbPassword = "12345";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            // Prepare a secure SQL query
            String sqlQuery = "SELECT * FROM lms_leavemanagement WHERE status=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, "Pending");

            ResultSet resultSet = preparedStatement.executeQuery();

            out.println("<center><h1>LeaveRequest</h1>");
            out.println("<style>\r\n" +
                    "table, th, td {\r\n" +
                    "  border: 1px solid black;\r\n" +
                    "}\r\n" +
                    ".btn {\r\n" +
                    "  background-color: #4CAF50;\r\n" +
                    "  color: white;\r\n" +
                    "  padding: 10px 24px;\r\n" +
                    "  margin: 10px 0;\r\n" +
                    "  border: none;\r\n" +
                    "  cursor: pointer;\r\n" +
                    "  width: 20%;\r\n" +
                    "}\r\n" +
                    ".btn:hover {\r\n" +
                    "  opacity: 0.8;\r\n" +
                    "}\r\n" +
                    "</style>");
            out.println("<center><table>");
            out.println("<tr><th>Employee ID</th><th>Leave Type</th><th>Start Date</th><th>End Date</th><th>Status</th><th>Reason</th><th>Action</th></tr>");
            while (resultSet.next()) {
                String eid = resultSet.getString("Eid");
                String leaveType = resultSet.getString("leavetype");
                String startDate = resultSet.getString("startdate");
                String endDate = resultSet.getString("enddate");
                String status = resultSet.getString("status");
                String reason = resultSet.getString("reason");
                out.println("<tr><td>" + eid + "</td><td>" + leaveType + "</td><td>" + startDate + "</td><td>" + endDate + "</td><td>" + status + "</td><td>" + reason + "</td>");
                out.println("<td><form action=\"ApproveRejectServlet\" method=\"POST\">");
                out.println("<input type=\"hidden\" name=\"eid\" value=\"" + eid + "\">");
                out.println("<input type=\"submit\" name=\"action\" value=\"Approve\" style=\"background-color: green;\">");
                out.println("<input type=\"submit\" name=\"action\" value=\"Reject\" style=\"background-color: red;\"></form></td></tr>");
            }
            out.println("</table></center>");
            out.println("<form action='LogoutServlet' method='GET'><center><input type='submit' value='Log out' style='background-color: red; color: white; padding: 5px 10px; font-size: smaller;'></center></form>");
            out.println("<div style='text-align: center;'><button onclick='goBack()' style='background-color: #17a2b8; color: white; padding: 5px 10px; font-size: smaller;'>Go Back</button></div>");
            out.println("<script>function goBack() {window.history.back();}</script>");
        } catch (SQLException e) {
            out.println("Error accessing database: " + e.getMessage());
            // Provide more specific error handling for different SQLException types
        } catch (ClassNotFoundException e) {
            // Catch specific exception for missing JDBC driver
            out.println("Error: JDBC driver not found. Please ensure the driver is included in your classpath.");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Handle any connection close errors
                out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}

