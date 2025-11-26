<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.CustomerDAO"%>
<%@page import="model.Customer"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 90%;
            max-width: 1000px;
            margin: 30px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #333333;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 10px;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .btn-view {
            text-decoration: none;
            color: #ffffff;
            background-color: #2196F3;
            padding: 6px 12px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .btn-view:hover {
            background-color: #0b7dda;
        }

        a.add-new {
            display: inline-block;
            margin-top: 15px;
            text-decoration: none;
            color: #ffffff;
            background-color: #4CAF50;
            padding: 8px 12px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        a.add-new:hover {
            background-color: #45a049;
        }

        @media screen and (max-width: 768px) {
            table, thead, tbody, th, td, tr {
                display: block;
            }

            tr {
                margin-bottom: 15px;
            }

            th {
                display: none;
            }

            td {
                text-align: right;
                padding-left: 50%;
                position: relative;
            }

            td::before {
                content: attr(data-label);
                position: absolute;
                left: 10px;
                width: 45%;
                padding-left: 10px;
                font-weight: bold;
                text-align: left;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Customer List</h2>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Registration Date</th>
                <th>View</th>
            </tr>
        </thead>
        <tbody>
        <%
            CustomerDAO dao = new CustomerDAO();
            List<Customer> customers = dao.getAllCustomers();
            for (Customer c : customers) {
        %>
            <tr>
                <td data-label="ID"><%= c.getId() %></td>
                <td data-label="Name"><%= c.getName() %></td>
                <td data-label="Phone"><%= c.getPhoneNumber() %></td>
                <td data-label="Email"><%= c.getEmail() %></td>
                <td data-label="Registration Date"><%= c.getRegistrationDate() %></td>
                <td data-label="View">
                    <a class="btn-view" href="customerDetails.jsp?id=<%=c.getId()%>">View</a>
                </td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <a class="add-new" href="registerCustomer.jsp">Add New Customer</a>
</div>
</body>
</html>
