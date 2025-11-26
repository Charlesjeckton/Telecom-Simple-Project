package servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String url = "jdbc:mysql://localhost:3307/telecom?useSSL=false&serverTimezone=UTC";
        String dbUser = "root";
        String dbPass = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);

            // Validate login
            String sql = "SELECT id, role FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String role = rs.getString("role");

                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", role);
                session.setAttribute("userId", userId);

                // 🔥 NEW: Fetch customerId
                int customerId = 0;

                String customerSql = "SELECT id FROM customers WHERE user_id=?";
                PreparedStatement custStmt = conn.prepareStatement(customerSql);
                custStmt.setInt(1, userId);
                ResultSet custRs = custStmt.executeQuery();

                if (custRs.next()) {
                    customerId = custRs.getInt("id");
                    session.setAttribute("customerId", customerId);
                }

                // Redirect by role
                if (role.equalsIgnoreCase("ADMIN")) {
                    response.sendRedirect("admin/dashboard.jsp");

                } else if (role.equalsIgnoreCase("CUSTOMER")) {
                    response.sendRedirect("customer/home.jsp");

                } else {
                    response.sendRedirect("login.jsp?error=Unknown role");
                }

            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Login error: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
