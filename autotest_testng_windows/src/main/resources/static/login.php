<?php
include 'config.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST['username'];
    $password = $_POST['password'];

    $sql = "SELECT * FROM users WHERE username='$username' AND password='$password'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $successMessage = "登录成功! 即将跳转到首页";
        header("refresh:1; url=1.html");
    } else {
        $errorMessage = "用户名或密码错误,请重新登录";
    }
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>登录页面</title>
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
    <h1>登录页面</h1>
    <form action="login.php" method="post">
        <label for="username">用户名:</label>
        <input type="text" name="username" required><br>
        <label for="password">密码:</label>
        <input type="password" name="password" required><br>
        <input type="submit" value="登录">
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
