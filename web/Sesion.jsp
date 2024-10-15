<html>
<head>
    <title>Inicio de Sesi�n</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin: 20px; }
    </style>
</head>
<body>
    <h2>Iniciar Sesi�n</h2>
    <form method="post" action="login">
        <label for="nombre_usuario">Nombre de Usuario:</label>
        <input type="text" id="nombre_usuario" name="nombre_usuario" required><br><br>

        <label for="password_hash">Contrase�a:</label>
        <input type="password" id="password_hash" name="password_hash" required><br><br>

        <input type="submit" value="Iniciar Sesi�n">
        <a href="Usuario.jsp">Registrar Nuevo Usuario</a>
    </form>
</body>
</html>
