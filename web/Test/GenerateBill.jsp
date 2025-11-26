<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.CustomerDAO"%>
<%@page import="model.Customer"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <title>Generate Bill</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 0; margin: 0; }
        .container { width: 90%; max-width: 600px; margin: 30px auto; background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input, select, button { width: 100%; padding: 8px; margin-top: 5px; border-radius: 5px; border: 1px solid #ccc; }
        button { background-color: #4CAF50; color: white; border: none; cursor: pointer; margin-top: 15px; }
        button:hover { background-color: #45a049; }
    </style>
</head>
<body>

<div class="container">
    <h2>Generate New Bill</h2>

    <form action="GenerateBillServlet" method="post">
        <label>Customer:</label>
        <select name="customerId" required>
            <option value="">-- Select Customer --</option>
            <%
                CustomerDAO customerDAO = new CustomerDAO();
                List<Customer> customers = customerDAO.getAllCustomers();
                for (Customer c : customers) {
            %>
            <option value="<%=c.getId()%>"><%=c.getName()%> (ID: <%=c.getId()%>)</option>
            <% } %>
        </select>

        <label>Amount:</label>
        <input type="number" name="amount" step="0.01" required>

        <label>Billing Date:</label>
        <input type="date" name="billingDate" required>

        <label>Paid:</label>
        <select name="paid" required>
            <option value="true">Yes</option>
            <option value="false">No</option>
        </select>

        <button type="submit">Generate Bill</button>
    </form>

    <br>
    <a href="listBills.jsp">View All Bills</a>
</div>

</body>
</html>
