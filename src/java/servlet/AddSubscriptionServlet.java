package servlet;

import dao.SubscriptionDAO;
import dao.ServiceDAO;
import dao.BillingDAO;
import model.Billing;
import model.Service;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/customer/addSubscription")
public class AddSubscriptionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // 1️⃣ Ensure user login
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

        try {
            int serviceId = Integer.parseInt(request.getParameter("service_id"));
            String startDate = request.getParameter("start_date");

            // 3️⃣ Validate start date
            if (startDate == null || startDate.trim().isEmpty()) {
                response.sendRedirect("addSubscription.jsp?error=Start+date+is+required");
                return;
            }

            // 4️⃣ Auto generate end date for customers (30 days later)
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = start.plusDays(30);
            String endDate = end.toString();

            // 5️⃣ Add subscription using NEW FULL signature
            boolean ok = subDAO.addSubscription(customerId, serviceId, startDate, endDate);

            if (ok) {

                // 6️⃣ Retrieve service info
                Service service = serviceDAO.getServiceById(serviceId);

                if (service != null) {
                    // 7️⃣ Create billing entry
                    Billing bill = new Billing();
                    bill.setCustomerId(customerId);
                    bill.setServiceId(serviceId);
                    bill.setAmount(service.getMonthlyFee());
                    bill.setBillingDate(startDate);
                    bill.setPaid(false);

                    billingDAO.generateBill(bill);
                }

                response.sendRedirect("subscriptions.jsp?success=Subscription+added");

            } else {
                response.sendRedirect("addSubscription.jsp?error=Failed+to+add+subscription");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addSubscription.jsp?error=Invalid+input");
        }
    }
}
