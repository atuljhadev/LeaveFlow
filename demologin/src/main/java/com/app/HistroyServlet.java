package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/HistroyServlet")
public class HistroyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String jdbcUrl = "jdbc:mysql://localhost:3306/lms";
		String dbUser = "root";
		String dbPassword = "12345";

		HttpSession session = request.getSession();
		String eid = (String) session.getAttribute("Eid");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
					Statement statement = connection.createStatement()) {

				String sqlQuery = "SELECT * FROM lms_leavemanagement WHERE Eid=" + eid;
				ResultSet resultSet = statement.executeQuery(sqlQuery);

				out.println("<center><h1>History</h1>");
				out.println("<table border=1 width=50% height=50%>");
				out.println(
						"<tr><th>Eid</th><th>Leave Type</th><th>Start Date</th><th>End Date</th><th>Status</th><th>Reason</th><th>Cancel Leave</th></tr>");

				while (resultSet.next()) {
					out.println("<tr><td>" + resultSet.getString("Eid") + "</td><td>" + resultSet.getString("leavetype")
							+ "</td><td>" + resultSet.getString("startdate") + "</td><td>"
							+ resultSet.getString("enddate") + "</td><td>" + resultSet.getString("status") + "</td><td>"
							+ resultSet.getString("reason")
							+ "</td><td><a href='./DeleteRowServlet'>Cancel Leave</a></td></tr>");
				}

				out.println("</table></center>");
				out.println(
						"<form action='LogoutServlet' method='GET'><center><input type='submit' value='Log out' style='background-color: red; color: white; padding: 10px 20px; font-size: larger;'></center></form>");
				out.println(
						"<div style='text-align: center;'><button onclick='goBack()' style='background-color: #17a2b8; color: white; padding: 10px 20px; font-size: larger;'>Go Back</button></div>");
				out.println("<script>function goBack() {window.history.back();}</script>");

				resultSet.close();
			}
		} catch (ClassNotFoundException | SQLException e) {
			out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
