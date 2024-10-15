<html>
<head>
    <title>Gestión de Usuarios</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container {
            margin-top: 10px;
        }
    </style>
    <script>
        function buscarUsuario() {
            const usuarioId = document.getElementById("usuario_id").value;

            if (usuarioId.trim() !== "") {
                fetch(`usuario`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', usuario_id: usuarioId })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Usuario no encontrado.") {
                        const [nombre_usuario, email, password_hash, rol_id, trabajador_id, cliente_id] = data.split(",");
                        document.getElementById("nombre_usuario").value = nombre_usuario;
                        document.getElementById("email").value = email;
                        document.getElementById("password_hash").value = password_hash;
                        document.getElementById("rol_id").value = rol_id;
                        document.getElementById("trabajador_id").value = trabajador_id === "null" ? "" : trabajador_id;
                        document.getElementById("cliente_id").value = cliente_id === "null" ? "" : cliente_id;
                    } else {
                        alert(data); // Si no se encuentra, muestra alerta
                        limpiarCampos(); // Limpia los campos
                    }
                })
                .catch(error => console.error('Error:', error));
            }
        }

        function limpiarCampos() {
            // Limpia los campos
            document.getElementById("nombre_usuario").value = ''; // Limpia el campo nombre
            document.getElementById("email").value = ''; // Limpia el campo email
            document.getElementById("password_hash").value = ''; // Limpia el campo password
            document.getElementById("rol_id").value = ''; // Limpia el campo rol
            document.getElementById("trabajador_id").value = ''; // Limpia el campo trabajador
            document.getElementById("cliente_id").value = ''; // Limpia el campo cliente
        }

        function updateForm() {
            const action = document.getElementById("action").value;
            limpiarCampos(); // Limpia campos al cambiar de opción

            if (action === "UPDATE" || action === "DELETE") {
                document.getElementById("usuario_id").disabled = false;
                document.getElementById("buscarBtn").style.display = "inline";
            } else {
                document.getElementById("buscarBtn").style.display = "none";
            }
        }
    </script>
</head>
<body>
    <h2>Gestión de Usuarios</h2>

    <form id="actionForm" method="post" action="usuario">
        <label for="action">Acción:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="nombre_usuario">Nombre de Usuario:</label>
        <input type="text" id="nombre_usuario" name="nombre_usuario" required><br><br>

        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required><br><br>

        <label for="password_hash">Contraseña:</label>
        <input type="text" id="password_hash" name="password_hash" required><br><br>

        <label for="rol_id">ID de Rol:</label>
        <input type="text" id="rol_id" name="rol_id" required><br><br>

        <label for="trabajador_id">ID de Trabajador:</label>
        <input type="text" id="trabajador_id" name="trabajador_id"><br><br>

        <label for="cliente_id">ID de Cliente:</label>
        <input type="text" id="cliente_id" name="cliente_id"><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarUsuario()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
