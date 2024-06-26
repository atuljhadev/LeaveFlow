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
import javax.servlet.http.HttpSession;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String INSERT_QUERY="INSERT INTO lms_leavemanagement(Eid,leavetype,startdate,enddate,status,reason)VALUES(?,?,?,?,'pending',?)";
	//private static final String INSERT_QUERY="INSERT INTO lms_leavemanagement(Eid,leavetype,startdate,enddate,status,reason)VALUES(101,'vacation','2024/02/24','2024/02/28','pending','zxx')";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String leavetype = request.getParameter("leavetype");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String reason = request.getParameter("reason");
		
		HttpSession session = request.getSession();
		String eid = (String) session.getAttribute("Eid");
		System.out.println(eid);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","12345");
				PreparedStatement ps = conn.prepareStatement(INSERT_QUERY)) {
				
				ps.setString(1, eid);
				ps.setString(2, leavetype);
				ps.setString(3, startdate);
				ps.setString(4, enddate);
				ps.setString(5, reason);
				
				int count = ps.executeUpdate();
				if(count == 0) {
					pw.println("Record not stored into Database");
				} else {
					response.sendRedirect("status.html");
				}
			} catch(SQLException e) {
				pw.println(e.getMessage());
				e.printStackTrace();
			}
		} catch(ClassNotFoundException e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
}
