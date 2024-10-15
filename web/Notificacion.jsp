<html>
<head>
    <title>Gestión de Notificaciones</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container { margin-top: 10px; }
    </style>
    <script>
        function buscarNotificacion() {
            const notificacionId = document.getElementById("notificacion_id").value;

            if (notificacionId.trim() !== "") {
                fetch(`notificacion`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', notificacion_id: notificacionId })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Notificación no encontrada.") {
                        const [mensaje, fecha, leido, usuario_id] = data.split(",");
                        document.getElementById("mensaje").value = mensaje;
                        document.getElementById("fecha").value = fecha;
                        document.getElementById("leido").checked = (leido === "true");
                        document.getElementById("usuario_id").value = usuario_id;
                    } else {
                        alert(data);
                        limpiarCampos();
                    }
                })
                .catch(error => console.error('Error:', error));
            }
        }

        function limpiarCampos() {
            document.getElementById("notificacion_id").value = '';
            document.getElementById("mensaje").value = '';
            document.getElementById("fecha").value = '';
            document.getElementById("leido").checked = false;
            document.getElementById("usuario_id").value = '';
        }

        function updateForm() {
            const action = document.getElementById("action").value;
            limpiarCampos();

            if (action === "UPDATE" || action === "DELETE") {
                document.getElementById("notificacion_id").disabled = false;
                document.getElementById("buscarBtn").style.display = "inline";
            } else {
                document.getElementById("buscarBtn").style.display = "none";
            }
        }
    </script>
</head>
<body>
    <h2>Gestión de Notificaciones</h2>

    <form id="actionForm" method="post" action="notificacion">
        <label for="action">Acción:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="notificacion_id">ID de la Notificación:</label>
        <input type="text" id="notificacion_id" name="notificacion_id" required><br><br>

        <label for="mensaje">Mensaje:</label>
        <input type="text" id="mensaje" name="mensaje"><br><br>

        <label for="fecha">Fecha:</label>
        <input type="date" id="fecha" name="fecha"><br><br>

        <label for="leido">Leído:</label>
        <input type="checkbox" id="leido" name="leido"><br><br>

        <label for="usuario_id">ID del Usuario:</label>
        <input type="text" id="usuario_id" name="usuario_id"><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarNotificacion()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
