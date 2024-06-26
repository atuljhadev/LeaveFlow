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

@WebServlet("/CancelLeaveServlet")
public class CancelLeaveServlet extends HttpServlet {

/**
 * 
 */
private static final long serialVersionUID = 1L;
private HttpServletRequest request;


public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
    PrintWriter out = res.getWriter();
    res.setContentType("text/html");
    out.println("<html><body>");
    HttpSession session = req.getSession(); 
    Object id = session.getAttribute("Eid");
    try {
    	Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","12345");
        Statement stmt = con.createStatement();
        String sqlQuery1 = "SELECT * FROM lms_leavemanagement where Eid=";
        String sqlQuery=sqlQuery1 + id; 
        ResultSet rs = stmt.executeQuery(sqlQuery);
        out.println("<form method = \"post\">");
        out.println("<center>");
        out.println("<table border=1 width=50% height=50%>");
        out.println("<tr><th align=\"center\">Eid </th><th align=\"center\">leavetype</th><th align=\"center\">startdate</th><th align=\\\"center\\\">enddate</th><th align=\\\"center\\\">status</th><th align=\\\"center\\\">reason</th><th align=\\\\\\\"center\\\\\\\">cancel leave</th><th></th><tr>");
        //HttpSession session=request.getSession();
    	//Object id =session.getAttribute("Eid"); 
       
        while (rs.next()) {
            String id1 = rs.getString("Eid");
            String leavetype = rs.getString("leavetype");
            String startdate = rs.getString("startdate"); 
            String enddate = rs.getString("enddate");
            String status = rs.getString("status");
            String reason = rs.getString("reason");
			//HttpSession session = req.getSession(true); 
            //session.setAttribute("Eid",Eid);
           
            out.println("<center><tr><td align=\"center\">" + id1 + "</td><td align=\"center\">" + leavetype + "</td><td align=\"center\">"+startdate+"</td><td align=\"center\">"+enddate+"\"</td><td align=\\\"center\\\">"+ status+" </td><td align=\"center\">" +reason+"</td><td align=\"center\"><a href = \"./DeleteRowServlet\">Select</a></td></tr></center>");
        }
        out.println("</table>");
        out.println("</form");
        out.println("<center>");
        out.println("</body></html>");
        con.close();
       }
        catch (Exception e) {
        e.printStackTrace();
        }
}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
}
} 
