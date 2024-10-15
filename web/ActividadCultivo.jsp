<html>
<head>
    <title>Gestión de Actividades de Cultivo</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container { margin-top: 10px; }
    </style>
    <script>
        function buscarActividadCultivo() {
            const actividadId = document.getElementById("actividad_id").value;

            if (actividadId.trim() !== "") {
                fetch(`actividadCultivo`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', actividad_id: actividadId })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Actividad de cultivo no encontrada.") {
                        const [nombre, descripcion, fecha, producto_id] = data.split(",");
                        document.getElementById("nombre").value = nombre;
                        document.getElementById("descripcion").value = descripcion;
                        document.getElementById("fecha").value = fecha;
                        document.getElementById("producto_id").value = producto_id;
                    } else {
                        alert(data);
                        limpiarCampos();
                    }
                })
                .catch(error => console.error('Error:', error));
            }
        }

        function limpiarCampos() {
            document.getElementById("actividad_id").value = '';
            document.getElementById("nombre").value = '';
            document.getElementById("descripcion").value = '';
            document.getElementById("fecha").value = '';
            document.getElementById("producto_id").value = '';
        }

        function updateForm() {
            const action = document.getElementById("action").value;
            limpiarCampos();

            if (action === "UPDATE" || action === "DELETE") {
                document.getElementById("actividad_id").disabled = false;
                document.getElementById("buscarBtn").style.display = "inline";
            } else {
                document.getElementById("buscarBtn").style.display = "none";
            }
        }
    </script>
</head>
<body>
    <h2>Gestión de Actividades de Cultivo</h2>

    <form id="actionForm" method="post" action="actividadCultivo">
        <label for="action">Acción:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="actividad_id">ID de la Actividad:</label>
        <input type="text" id="actividad_id" name="actividad_id" required><br><br>

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre"><br><br>

        <label for="descripcion">Descripción:</label>
        <input type="text" id="descripcion" name="descripcion"><br><br>

        <label for="fecha">Fecha:</label>
        <input type="date" id="fecha" name="fecha"><br><br>

        <label for="producto_id">ID del Producto:</label>
        <input type="text" id="producto_id" name="producto_id"><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarActividadCultivo()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
