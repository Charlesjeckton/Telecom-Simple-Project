<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.CustomerDAO"%>
<%@page import="model.Customer"%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 400px;
            max-width: 95%;
            margin: 50px auto;
            padding: 25px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #333333;
        }

        .detail {
            margin-bottom: 15px;
            font-size: 16px;
        }

        .detail b {
            color: #4CAF50;
            width: 150px;
            display: inline-block;
        }

        .btn-back {
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
            color: #ffffff;
            background-color: #4CAF50;
            padding: 10px 15px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .btn-back:hover {
            background-color: #45a049;
        }

        .error {
            text-align: center;
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="container">
<%
    String idParam = request.getParameter("id");
    if (idParam != null) {
        int id = Integer.parseInt(idParam);
        CustomerDAO dao = new CustomerDAO();
        Customer c = dao.getCustomerById(id);

        if (c != null) {
%>
            <h2>Customer Details</h2>
            <div class="detail"><b>ID:</b> <%=c.getId()%></div>
            <div class="detail"><b>Name:</b> <%=c.getName()%></div>
            <div class="detail"><b>Phone:</b> <%=c.getPhoneNumber()%></div>
            <div class="detail"><b>Email:</b> <%=c.getEmail()%></div>
            <div class="detail"><b>Registration Date:</b> <%=c.getRegistrationDate()%></div>

            <a class="btn-back" href="listCustomers.jsp">Back to List</a>
<%
        } else {
%>
            <p class="error">Customer not found!</p>
            <a class="btn-back" href="listCustomers.jsp">Back to List</a>
<%
        }
    } else {
%>
    <p class="error">Invalid customer ID!</p>
    <a class="btn-back" href="listCustomers.jsp">Back to List</a>
<%
    }
%>
</div>

</body>
</html>
