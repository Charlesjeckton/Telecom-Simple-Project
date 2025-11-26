<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.SubscriptionDAO"%>
<%@page import="model.Subscription"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <title>Subscriptions List</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f9f9f9; margin: 0; padding: 0; }
        .container { width: 90%; max-width: 900px; margin: 30px auto; padding: 20px; background: #fff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        a.btn { text-decoration: none; color: white; padding: 6px 12px; border-radius: 5px; }
        a.deactivate { background-color: #f44336; }
        a.deactivate:hover { background-color: #d32f2f; }
        a.activate { background-color: #4CAF50; }
        a.activate:hover { background-color: #45a049; }
    </style>
</head>
<body>
<div class="container">
    <h2>Subscriptions List</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Customer ID</th>
                <th>Service ID</th>
                <th>Name</th>
                <th>Start Date</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <%
            SubscriptionDAO dao = new SubscriptionDAO();
            List<Subscription> subscriptions = dao.getAllSubscriptions();
            for (Subscription s : subscriptions) {
        %>
            <tr>
                <td><%= s.getId() %></td>
                <td><%= s.getCustomerId() %></td>
                <td><%= s.getServiceId() %></td>
                <td><%= s.getName() %></td>
                <td><%= s.getStartDate() %></td>
                <td><%= s.getStatus() %></td>
                <td>
                    <% if ("active".equals(s.getStatus())) { %>
                        <a class="btn deactivate" href="deactivateSubscriptionServlet?id=<%=s.getId()%>">Deactivate</a>
                    <% } else { %>
                        <span style="color:gray;">Inactive</span>
                    <% } %>
                </td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <br>
    <a class="btn activate" href="activateSubscription.jsp">Activate New Subscription</a>
</div>
</body>
</html>
