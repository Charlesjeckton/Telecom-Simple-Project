package servlet;

import dao.BillingDAO;
import dao.SubscriptionDAO;
import dao.ServiceDAO;

import model.Billing;
import model.Subscription;
import model.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/GenerateBillServlet")
public class GenerateBillServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int customerId = Integer.parseInt(req.getParameter("customerId"));

            // DAOs
            SubscriptionDAO subDao = new SubscriptionDAO();
            ServiceDAO serviceDao = new ServiceDAO();

            // Fetch active subscriptions
            List<Subscription> activeSubs = subDao.getActiveSubscriptionsByCustomer(customerId);

            double totalAmount = 0.0;

            // Calculate bill total
            for (Subscription sub : activeSubs) {
                Service service = serviceDao.getServiceById(sub.getServiceId());
                if (service != null) {
                    totalAmount += service.getMonthlyFee();
                }
            }

            // Create bill
            Billing bill = new Billing();
            bill.setCustomerId(customerId);
            bill.setAmount(totalAmount);
            bill.setBillingDate(LocalDate.now().toString());
            bill.setPaid(false);

            // Save to DB
            BillingDAO billingDAO = new BillingDAO();
            billingDAO.generateBill(bill);

            // Redirect to bill list page
            resp.sendRedirect("billingHistory.jsp?customerId=" + customerId);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?msg=" + e.getMessage());
        }
    }
}
