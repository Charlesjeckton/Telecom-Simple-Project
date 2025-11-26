<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.BillingDAO"%>
<%@page import="model.Billing"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <title>Billing List</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; margin: 0; padding: 0; }
        .container { width: 90%; max-width: 1000px; margin: 30px auto; padding: 20px; background-color: #fff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        .btn-back { text-decoration: none; color: #fff; background-color: #4CAF50; padding: 8px 12px; border-radius: 5px; display: inline-block; margin-top: 15px; }
        .btn-back:hover { background-color: #45a049; }
        @media screen and (max-width: 768px) {
            table, thead, tbody, th, td, tr { display: block; }
            tr { margin-bottom: 15px; }
            th { display: none; }
            td { text-align: right; padding-left: 50%; position: relative; }
            td::before { content: attr(data-label); position: absolute; left: 10px; width: 45%; font-weight: bold; text-align: left; }
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Billing List</h2>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Customer ID</th>
                <th>Amount</th>
                <th>Billing Date</th>
                <th>Paid</th>
            </tr>
        </thead>
        <tbody>
        <%
            BillingDAO dao = new BillingDAO();
            List<Billing> bills = dao.getAllBills();
            for (Billing b : bills) {
        %>
            <tr>
                <td data-label="ID"><%=b.getId()%></td>
                <td data-label="Customer ID"><%=b.getCustomerId()%></td>
                <td data-label="Amount"><%=b.getAmount()%></td>
                <td data-label="Billing Date"><%=b.getBillingDate()%></td>
                <td data-label="Paid"><%=b.isPaid() ? "Yes" : "No"%></td>
            </tr>
        <% } %>
        </tbody>
    </table>

    <a class="btn-back" href="GenerateBill.jsp">Generate New Bill</a>
</div>

</body>
</html>
