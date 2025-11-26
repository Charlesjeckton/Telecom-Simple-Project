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

@WebServlet("/customer/activateSubscription")
public class ActivateSubscriptionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // 1️⃣ User must be logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("../login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        SubscriptionDAO subDAO = new SubscriptionDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        BillingDAO billingDAO = new BillingDAO();

        // 2️⃣ Fetch customer ID
        int customerId = subDAO.getCustomerIdByUserId(userId);
        if (customerId == 0) {
            response.sendRedirect("../error.jsp");
            return;
        }

        // 3️⃣ Get subscription ID and start date
        int subId = Integer.parseInt(request.getParameter("id"));
        String startDate = request.getParameter("start_date");

        // 4️⃣ Ensure the subscription belongs to this customer
        if (!subDAO.customerOwnsSubscription(customerId, subId)) {
            response.sendRedirect("subscriptions.jsp?error=Unauthorized+action");
            return;
        }

        // 5️⃣ Validate start date
        if (startDate == null || startDate.trim().isEmpty()) {
            response.sendRedirect("activateSubscriptionForm.jsp?id=" + subId + "&error=Start+date+is+required");
            return;
        }

        // 6️⃣ Auto-calc end date (30 days later)
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = start.plusDays(30);
        String endDate = end.toString();

        // 7️⃣ Activate subscription using (id, start, end)
        boolean ok = subDAO.activateSubscription(subId, startDate, endDate);

        if (!ok) {
            response.sendRedirect("activateSubscriptionForm.jsp?id=" + subId + "&error=Activation+failed");
            return;
        }

        // 8️⃣ Generate billing for activation
        int serviceId = subDAO.getServiceIdBySubscriptionId(subId);
        Service service = serviceDAO.getServiceById(serviceId);

        if (service != null) {
            Billing bill = new Billing();
            bill.setCustomerId(customerId);
            bill.setServiceId(serviceId);
            bill.setAmount(service.getMonthlyFee());
            bill.setBillingDate(startDate);
            bill.setPaid(false);

            billingDAO.generateBill(bill);
        }

        // 9️⃣ Redirect success
        response.sendRedirect("subscriptions.jsp?success=Subscription+Activated");
    }
}
