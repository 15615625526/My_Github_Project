<?php
include 'config.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST['username'];
    $password = $_POST['password'];
    $confirm_password = $_POST['confirm_password'];

    if ($password !== $confirm_password) {
        $errorMessage = "两次输入的密码不匹配，请重新输入";
    } else {
        $sql = "INSERT INTO users (username, password) VALUES ('$username', '$password')";

        if ($conn->query($sql) === TRUE) {
            $successMessage = "注册成功! 即将跳转到登录页面";
            header("refresh:1; url=login.php");
        } else {
            $errorMessage = "用户名已存在，请使用其它用户名:<br>" . $conn->error;
        }
    }
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>注册页面</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .container {
            width: 100%;
            max-width: 300px;
            margin: 0 auto;
            text-align: center;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        input[type="submit"] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .success-message {
            color: green;
            font-size: 1.5em;
            font-weight: bold;
            margin-top: 15px;
        }

        .error-message {
            color: red;
            font-size: 1.1em;
            margin-top: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>注册页面</h1>
    <form action="register.php" method="post">
        <label for="username">用户名:</label>
        <input type="text" name="username" required><br>
        <label for="password">密码:</label>
        <input type="password" name="password" required><br>
        <label for="confirm_password">确认密码:</label>
        <input type="password" name="confirm_password" required><br>
        <input type="submit" value="注册">
    </form>
    <?php if (isset($successMessage)): ?>
        <div class="success-message"><?php echo $successMessage; ?></div>
    <?php endif; ?>
    <?php if (isset($errorMessage)): ?>
        <div class="error-message"><?php echo $errorMessage; ?></div>
    <?php endif; ?>
</div>
</body>
</html>
