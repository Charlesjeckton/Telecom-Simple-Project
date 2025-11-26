package servlet;

import dao.SubscriptionDAO;
import dao.ServiceDAO;
import dao.BillingDAO;
import model.Billing;
import model.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/subscription")
public class AdminSubscriptionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("subscriptions.jsp?error=Invalid+action");
            return;
        }

        if (action.equals("add")) {
            addSubscription(request, response);
        } else {
            response.sendRedirect("subscriptions.jsp?error=Unknown+action");
        }
    }

    private void addSubscription(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            int customerId = Integer.parseInt(request.getParameter("customer_id"));
            int serviceId = Integer.parseInt(request.getParameter("service_id"));
            String startDate = request.getParameter("start_date");
            String endDate = request.getParameter("end_date");

            if (startDate == null || startDate.trim().isEmpty()) {
                response.sendRedirect("addSubscription.jsp?error=Start+date+is+required");
                return;
            }

            // Auto-generate end date if empty (30 days from start)
            if (endDate == null || endDate.trim().isEmpty()) {
                LocalDate s = LocalDate.parse(startDate);
                endDate = s.plusDays(30).toString();
            }

            SubscriptionDAO subDAO = new SubscriptionDAO();
            ServiceDAO serviceDAO = new ServiceDAO();
            BillingDAO billingDAO = new BillingDAO();

            // 1️⃣ Add subscription
            boolean subAdded = subDAO.addSubscription(customerId, serviceId, startDate, endDate);

            if (!subAdded) {
                response.sendRedirect("addSubscription.jsp?error=Failed+to+add+subscription");
                return;
            }

            // 2️⃣ Fetch service price
            Service service = serviceDAO.getServiceById(serviceId);

            if (service == null) {
                System.err.println("Service not found for billing, ID: " + serviceId);
                response.sendRedirect("subscriptions.jsp?success=Subscription+added+but+billing+failed");
                return;
            }

            double price = service.getMonthlyFee(); // ✔ use correct field

            // 3️⃣ Create billing
            Billing bill = new Billing();
            bill.setCustomerId(customerId);
            bill.setServiceId(serviceId);
            bill.setAmount(price);
            bill.setBillingDate(startDate);
            bill.setPaid(false);

            boolean billCreated = billingDAO.generateBill(bill);

            if (!billCreated) {
                System.err.println("❌ Billing creation failed for customer " + customerId + ", service " + serviceId);
            } else {
                System.out.println("✅ Billing created for customer " + customerId + ", service " + serviceId);
            }

            response.sendRedirect("subscriptions.jsp?success=Subscription+and+billing+added");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addSubscription.jsp?error=Invalid+input");
        }
    }
}
