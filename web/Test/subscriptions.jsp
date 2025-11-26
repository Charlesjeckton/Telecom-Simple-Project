<%@ page import="java.util.List" %>
<%@ page import="model.Subscription" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Subscriptions</title>
    <style>
        body { font-family: Arial; padding: 30px; }
        table {
            width: 100%; border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        button {
            padding: 6px 12px;
            background: crimson;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>

<h2>Active Subscriptions</h2>

<table>
    <tr>
        <th>Plan</th>
        <th>Start Date</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <%
        List<Subscription> list = (List<Subscription>) request.getAttribute("subscriptions");

        if (list != null) {
            for (Subscription s : list) {
    %>

    <tr>
        <td><%= s.getPlanName() %></td>
        <td><%= s.getStartDate() %></td>
        <td><%= s.getStatus() %></td>
        <td>
            <form action="deactivateSubscription" method="post">
                <input type="hidden" name="id" value="<%= s.getId() %>">
                <button type="submit">Deactivate</button>
            </form>
        </td>
    </tr>

    <%      }
        }
    %>

</table>

</body>
</html>
