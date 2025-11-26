<%@ page import="dao.SubscriptionDAO" %>
<%@ page import="model.Subscription" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int subscriptionId = Integer.parseInt(request.getParameter("id"));

    SubscriptionDAO dao = new SubscriptionDAO();
    Subscription sub = dao.getSubscriptionById(subscriptionId);

    if (sub == null) {
        response.sendRedirect("error.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Activate Subscription</title>
    <link rel="stylesheet" href="assets/bootstrap.min.css">
</head>
<body class="bg-light">

<div class="container mt-4">
    <h2 class="mb-4">Activate Subscription</h2>

    <div class="card">
        <div class="card-header bg-primary text-white">
            Confirm Activation
        </div>

        <div class="card-body">

            <form action="activateSubscription" method="post">

                <input type="hidden" name="id" value="<%= sub.getId() %>">

                <!-- SERVICE NAME ONLY -->
                <div class="mb-3">
                    <label class="form-label">Service:</label>
                    <input type="text" class="form-control" value="<%= sub.getName() %>" disabled>
                </div>

                <!-- START DATE -->
                <div class="mb-3">
                    <label class="form-label">Start Date:</label>
                    <input type="date" name="start_date" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-success">Activate</button>

            </form>

        </div>
    </div>
</div>

</body>
</html>
