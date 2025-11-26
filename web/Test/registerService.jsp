<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Service</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; }
        .container { width: 400px; margin: 50px auto; padding: 20px; background-color: #fff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; }
        label { display: block; margin-top: 10px; }
        input, textarea { width: 100%; padding: 8px; margin-top: 5px; border-radius: 5px; border: 1px solid #ccc; }
        button { margin-top: 15px; width: 100%; padding: 10px; background-color: #4CAF50; color: #fff; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background-color: #45a049; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Add New Service</h2>
        <form action="registerService" method="post">
            <label>Name:</label>
            <input type="text" name="name" required>

            <label>Description:</label>
            <textarea name="description" rows="4" required></textarea>

            <label>Monthly Fee:</label>
            <input type="number" step="0.01" name="monthlyFee" required>

            <button type="submit">Add Service</button>
        </form>
    </div>
</body>
</html>
