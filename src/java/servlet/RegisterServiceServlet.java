package servlet;

import dao.ServiceDAO;
import model.Service;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/registerService")
public class RegisterServiceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double monthlyFee = Double.parseDouble(req.getParameter("monthlyFee"));

        Service s = new Service();
        s.setName(name);
        s.setDescription(description);
        s.setMonthlyFee(monthlyFee);

        ServiceDAO dao = new ServiceDAO();
        boolean success = dao.addService(s);

        if (success) {
            res.sendRedirect("listServices.jsp");
        } else {
            res.getWriter().println("Failed to add service. Please try again.");
        }
    }
}
