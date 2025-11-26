<%@ include file="includes/adminTheme.jspf" %>
<%@ include file="includes/adminSidebar.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add New Service</title>

    <style>
        .main-content {
            margin-left: 260px; /* Same as sidebar width */
            padding: 20px;
        }
    </style>
</head>

<body>

<div class="main-content">

    <h2 class="mb-4">Add New Service</h2>

    <% if (request.getParameter("error") != null) { %>
        <div class="alert alert-danger">
            <%= request.getParameter("error") %>
        </div>
    <% } %>

    <div class="card shadow-sm">
        <div class="card-body">

            <form action="service" method="post">

                <!-- Action -->
                <input type="hidden" name="action" value="add">

                <div class="mb-3">
                    <label class="form-label">Service Name</label>
                    <input type="text" name="name" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Description</label>
                    <textarea name="description" class="form-control" rows="3" required></textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label">Monthly Fee</label>
                    <input type="number" step="0.01" name="monthly_fee" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-primary">Add Service</button>
                <a href="services.jsp" class="btn btn-secondary">Cancel</a>

            </form>

        </div>
    </div>

</div>

</body>
</html>
