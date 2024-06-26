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

@WebServlet("/DeleteRowServlet")
public class DeleteRowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        String jdbcUrl = "jdbc:mysql://localhost:3306/lms";
        String dbUser = "root";
        String dbPassword = "12345";

        HttpSession session = req.getSession();
        String eid = (String) session.getAttribute("Eid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
                 PreparedStatement st = con.prepareStatement("DELETE FROM lms_leavemanagement WHERE Eid = ?")) {

                st.setString(1, eid);
                int i = st.executeUpdate();

                if(i == 0) {
                    pw.println("<br>Row has been deleted successfully.");
                } else {
                    pw.println("Deleting row...");
                }
            }
        } catch (SQLException sx) {
            pw.println(sx);
        } catch (ClassNotFoundException cx) {
            pw.println(cx);
        } finally {
            pw.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
