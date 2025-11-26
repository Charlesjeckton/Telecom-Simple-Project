<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Register Customer</title>
    <style>
        body { font-family: Arial; background: #f5f5f5; padding: 40px; }
        .container {
            width: 450px; margin: auto; background: white; padding: 20px;
            border-radius: 10px; box-shadow: 0px 4px 10px rgba(0,0,0,0.1);
        }
        label { font-weight: bold; }
        input { width: 100%; padding: 10px; margin-bottom: 10px; }
        button {
            width: 100%; padding: 10px; background: #2d89ef;
            color: white; border: none; border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Register New Customer</h2>

    <form action="registerCustomer" method="post">

        <label>Full Name</label>
        <input type="text" name="name" required>

        <label>Phone Number</label>
        <input type="text" name="phone" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Username</label>
        <input type="text" name="username" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <button type="submit">Register</button>

    </form>

</div>

</body>
</html>
