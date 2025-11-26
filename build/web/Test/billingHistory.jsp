<%@ page import="java.util.List" %>
<%@ page import="model.Bill" %>

<!DOCTYPE html>
<html>
<head>
    <title>Billing History</title>
    <style>
        body { font-family: Arial; padding: 30px; }
        table {
            width: 100%; border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 10px;
        }
    </style>
</head>
<body>

<h2>Billing History</h2>

<table>
    <tr>
        <th>Date</th>
        <th>Amount</th>
        <th>Status</th>
    </tr>

    <%
        List<Bill> bills = (List<Bill>) request.getAttribute("bills");

        if (bills != null) {
            for (Bill b : bills) {
    %>

    <tr>
        <td><%= b.getBillingDate() %></td>
        <td>$<%= b.getAmount() %></td>
        <td><%= b.getStatus() %></td>
    </tr>

    <%      }
        }
    %>

</table>

</body>
</html>
