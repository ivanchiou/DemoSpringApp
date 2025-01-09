<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Login success page</title>
</head>
<body>
    <h1>This is login success page</h1>
    <p>name: ${name}</p>
    <p>email: ${email}</p>
    <p>authorities: ${authorities}</p>
    <p>token: ${token}</p>

    <button onclick="logout()">Logout</button>

    <script>
        function logout() {
            fetch('/api/auth/logout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if(response.ok) {
                    window.location.href = '/page/login'; //Redirect to login page after logout
                } else {
                    alert('Logout failed!');
                }
            })
        }
    </script>
</body>
</html>