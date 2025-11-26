<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="includes/adminTheme.jspf" %>
<%@ include file="includes/adminSidebar.jspf" %>

<%@ page import="java.util.*" %>
<%@ page import="dao.SubscriptionDAO" %>
<%@ page import="dao.CustomerDAO" %>
<%@ page import="dao.ServiceDAO" %>
<%@ page import="model.Subscription" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Service" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin • Subscription Management</title>

    <style>
        .main-content {
            margin-left: 260px; /* same as sidebar */
            padding: 25px;
        }
        .table-actions {
            display: flex;
            gap: 6px;
        }
    </style>
</head>

<body>

<div class="main-content">

    <!-- PAGE HEADER -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">Subscription Management</h2>
        <a href="addSubscription.jsp" class="btn btn-primary">+ Add New Subscription</a>
    </div>

    <!-- GLOBAL ALERTS (Servlet -> request.setAttribute) -->
    <c:if test="${not empty message}">
        <div class="alert alert-${messageType eq 'success' ? 'success' : 'danger'} alert-dismissible fade show">
            <strong>
                <c:choose>
                    <c:when test="${messageType eq 'success'}">Success:</c:when>
                    <c:otherwise>Error:</c:otherwise>
                </c:choose>
            </strong>
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <!-- ALERTS VIA REDIRECT (?success=...) -->
    <c:if test="${not empty param.success}">
        <div class="alert alert-success alert-dismissible fade show">
            <strong>Success:</strong> ${param.success}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <c:if test="${not empty param.error}">
        <div class="alert alert-danger alert-dismissible fade show">
            <strong>Error:</strong> ${param.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <!-- LOAD ALL SUBSCRIPTIONS -->
    <%
        SubscriptionDAO subDao = new SubscriptionDAO();
        CustomerDAO custDao = new CustomerDAO();
        ServiceDAO servDao = new ServiceDAO();

        List<Subscription> subs = subDao.getAllSubscriptions();

        // Cache customers & services to improve efficiency
        Map<Integer, Customer> customerCache = new HashMap<>();
        Map<Integer, Service> serviceCache = new HashMap<>();
    %>

    <!-- TABLE CARD -->
    <div class="card shadow-sm">
        <div class="card-body">

            <table class="table table-hover table-bordered align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Customer</th>
                        <th>Service</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Status</th>
                        <th style="width: 160px;">Actions</th>
                    </tr>
                </thead>

                <tbody>
                <%
                    if (subs != null && !subs.isEmpty()) {
                        for (Subscription s : subs) {

                            Customer c = customerCache.get(s.getCustomerId());
                            if (c == null) {
                                c = custDao.getCustomerById(s.getCustomerId());
                                customerCache.put(s.getCustomerId(), c);
                            }

                            Service sv = serviceCache.get(s.getServiceId());
                            if (sv == null) {
                                sv = servDao.getServiceById(s.getServiceId());
                                serviceCache.put(s.getServiceId(), sv);
                            }
                %>

                    <tr>
                        <td><%= (c != null) ? c.getName() : "Unknown Customer" %></td>
                        <td><%= (sv != null) ? sv.getName() : "Unknown Service" %></td>
                        <td><%= s.getStartDate() %></td>
                        <td><%= (s.getEndDate() == null) ? "-" : s.getEndDate() %></td>

                        <td>
                            <span class="badge bg-<%= s.getStatus().equalsIgnoreCase("Active") ? "success" : "secondary" %>">
                                <%= s.getStatus() %>
                            </span>
                        </td>

                        <td class="table-actions">
                            <a href="editSubscription.jsp?id=<%= s.getId() %>" class="btn btn-warning btn-sm">
                                Edit
                            </a>

                            <form action="../admin/subscription" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="deactivate">
                                <input type="hidden" name="id" value="<%= s.getId() %>">

                                <button class="btn btn-danger btn-sm"
                                        onclick="return confirm('Are you sure you want to deactivate this subscription?');">
                                    Deactivate
                                </button>
                            </form>
                        </td>
                    </tr>

                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="6" class="text-center text-muted">No subscriptions found.</td>
                    </tr>
                <%
                    }
                %>
                </tbody>

            </table>

        </div>
    </div>

</div>

</body>
</html>
