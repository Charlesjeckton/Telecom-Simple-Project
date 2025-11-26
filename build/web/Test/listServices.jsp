<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.ServiceDAO"%>
<%@page import="model.Service"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <title>Services List</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; }
        .container { width: 90%; max-width: 900px; margin: 30px auto; padding: 20px; background-color: #fff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #4CAF50; color: #fff; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        a.add-new { display: inline-block; margin-top: 15px; text-decoration: none; color: #fff; background-color: #4CAF50; padding: 8px 12px; border-radius: 5px; }
        a.add-new:hover { background-color: #45a049; }
    </style>
</head>
<body>
<div class="container">
    <h2>Services List</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Monthly Fee</th>
            </tr>
        </thead>
        <tbody>
        <%
            ServiceDAO dao = new ServiceDAO();
            List<Service> services = dao.getAllServices();
            for(Service s : services){
        %>
            <tr>
                <td><%= s.getId() %></td>
                <td><%= s.getName() %></td>
                <td><%= s.getDescription() %></td>
                <td><%= s.getMonthlyFee() %></td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <a class="add-new" href="registerService.jsp">Add New Service</a>
</div>
</body>
</html>
