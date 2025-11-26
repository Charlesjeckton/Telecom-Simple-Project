package servlet.admin;

import dao.SubscriptionDAO;
import dao.BillingDAO;
import dao.ServiceDAO;

import model.Subscription;
import model.Billing;
import model.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/admin/subscription")
public class AdminSubscriptionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        SubscriptionDAO sdao = new SubscriptionDAO();

        if (action == null || action.isEmpty()) {
            response.sendRedirect("subscriptions.jsp?error=No+action+specified");
            return;
        }

        switch (action) {

            // ======================================================
            // ADD SUBSCRIPTION + CREATE BILL
            // ======================================================
            case "add":
                try {
                    int customerId = Integer.parseInt(request.getParameter("customer_id"));
                    int serviceId = Integer.parseInt(request.getParameter("service_id"));
                    String startDate = request.getParameter("start_date");
                    String endDate = request.getParameter("end_date");

                    if (startDate == null || startDate.isEmpty()) {
                        response.sendRedirect("addSubscription.jsp?error=Start+date+required");
                        return;
                    }

                    boolean added = sdao.addSubscription(customerId, serviceId, startDate, endDate);

                    if (added) {

                        // ---------------------------------------------------------
                        // Create FIRST billing immediately after subscription
                        // ---------------------------------------------------------
                        ServiceDAO serviceDAO = new ServiceDAO();
                        BillingDAO billingDAO = new BillingDAO();

                        Service service = serviceDAO.getServiceById(serviceId);

                        if (service != null) {
                            Billing bill = new Billing();
                            bill.setCustomerId(customerId);
                            bill.setServiceId(serviceId);
                            bill.setAmount(service.getMonthlyFee());
                            bill.setBillingDate(LocalDate.now().toString());
                            bill.setPaid(false);

                            billingDAO.generateBill(bill);  // INSERT BILL
                        }

                        response.sendRedirect("subscriptions.jsp?success=Subscription+added");
                    } else {
                        response.sendRedirect("addSubscription.jsp?error=Failed+to+add");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("addSubscription.jsp?error=Invalid+input");
                }
                break;



            // ======================================================
            // UPDATE SUBSCRIPTION
            // ======================================================
            case "update":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    int customerId = Integer.parseInt(request.getParameter("customer_id"));
                    int serviceId = Integer.parseInt(request.getParameter("service_id"));
                    String startDate = request.getParameter("start_date");
                    String endDate = request.getParameter("end_date");
                    String status = request.getParameter("status");

                    if (startDate == null || startDate.isEmpty()) {
                        response.sendRedirect("editSubscription.jsp?id=" + id + "&error=Start+date+required");
                        return;
                    }

                    boolean updated = sdao.updateSubscription(id, customerId, serviceId, startDate, endDate, status);

                    if (updated) {
                        response.sendRedirect("subscriptions.jsp?success=Subscription+updated");
                    } else {
                        response.sendRedirect("editSubscription.jsp?id=" + id + "&error=Update+failed");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("subscriptions.jsp?error=Invalid+update+input");
                }
                break;



            // ======================================================
            // DEACTIVATE SUBSCRIPTION
            // ======================================================
            case "deactivate":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));

                    boolean ok = sdao.deactivateSubscription(id);

                    if (ok) {
                        response.sendRedirect("subscriptions.jsp?success=Subscription+deactivated");
                    } else {
                        response.sendRedirect("subscriptions.jsp?error=Deactivation+failed");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("subscriptions.jsp?error=Invalid+subscription+ID");
                }
                break;



            // ======================================================
            // ACTIVATE SUBSCRIPTION
            // ======================================================
            case "activate":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String startDate = request.getParameter("start_date");
                    String endDate = request.getParameter("end_date");

                    if (startDate == null || startDate.isEmpty()) {
                        response.sendRedirect("activateSubscription.jsp?id=" + id + "&error=Start+date+required");
                        return;
                    }

                    boolean ok = sdao.activateSubscription(id, startDate, endDate);

                    if (ok) {
                        response.sendRedirect("subscriptions.jsp?success=Subscription+activated");
                    } else {
                        response.sendRedirect("activateSubscription.jsp?id=" + id + "&error=Activation+failed");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("subscriptions.jsp?error=Invalid+activation+input");
                }
                break;



            // ======================================================
            // UNKNOWN ACTION
            // ======================================================
            default:
                response.sendRedirect("subscriptions.jsp?error=Unknown+action");
        }
    }
}
