<html>
<head>
    <title>Inicio de Sesión</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin: 20px; }
    </style>
</head>
<body>
    <h2>Iniciar Sesión</h2>
    <form method="post" action="login">
        <label for="nombre_usuario">Nombre de Usuario:</label>
        <input type="text" id="nombre_usuario" name="nombre_usuario" required><br><br>

        <label for="password_hash">Contraseña:</label>
        <input type="password" id="password_hash" name="password_hash" required><br><br>

        <input type="submit" value="Iniciar Sesión">
        <a href="Usuario.jsp">Registrar Nuevo Usuario</a>
    </form>
</body>
</html>
