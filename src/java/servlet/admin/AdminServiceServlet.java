package servlet.admin;

import dao.ServiceDAO;
import model.Service;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/service")
public class AdminServiceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        ServiceDAO dao = new ServiceDAO();

        if (action == null || action.trim().isEmpty()) {
            response.sendRedirect("services.jsp?error=No+action+provided");
            return;
        }

        switch (action) {

            // --------------------------------------------------------
            // ADD SERVICE
            // --------------------------------------------------------
            case "add":
                try {
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");
                    double fee = Double.parseDouble(request.getParameter("monthly_fee"));

                    Service sAdd = new Service(name, description, fee);

                    boolean added = dao.addService(sAdd);

                    if (added) {
                        response.sendRedirect("services.jsp?success=Service+added");
                    } else {
                        response.sendRedirect("addService.jsp?error=Failed+to+add+service");
                    }
                } catch (Exception e) {
                    response.sendRedirect("addService.jsp?error=Invalid+input");
                }
                break;


            // --------------------------------------------------------
            // UPDATE SERVICE
            // --------------------------------------------------------
            case "update":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");
                    double fee = Double.parseDouble(request.getParameter("monthly_fee"));

                    Service sUpdate = new Service(id, name, description, fee, true);

                    boolean updated = dao.updateService(sUpdate);

                    if (updated) {
                        response.sendRedirect("services.jsp?success=Service+updated");
                    } else {
                        response.sendRedirect("editService.jsp?id=" + id + "&error=Update+failed");
                    }
                } catch (Exception e) {
                    response.sendRedirect("services.jsp?error=Invalid+update+input");
                }
                break;


            // --------------------------------------------------------
            // DELETE SERVICE
            // --------------------------------------------------------
            case "delete":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));

                    boolean deleted = dao.deleteService(id);

                    if (deleted) {
                        response.sendRedirect("services.jsp?success=Service+deleted");
                    } else {
                        response.sendRedirect("services.jsp?error=Failed+to+delete");
                    }
                } catch (Exception e) {
                    response.sendRedirect("services.jsp?error=Invalid+delete+input");
                }
                break;


            // --------------------------------------------------------
            // UNKNOWN ACTION
            // --------------------------------------------------------
            default:
                response.sendRedirect("services.jsp?error=Unknown+action");
        }
    }
}
