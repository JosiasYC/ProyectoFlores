<html>
<head>
    <title>Gestión de Inventario de Herramientas</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container {
            margin-top: 10px;
        }
    </style>
    <script>
        function buscarHerramienta() {
            const nombre = document.getElementById("nombre").value;

            if (nombre.trim() !== "") {
                fetch(`inventario`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', nombre: nombre })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Herramienta no encontrada.") {
                        const [descripcion, cantidad] = data.split(","); // Asegúrate de que el servlet retorne los datos en este formato
                        document.getElementById("descripcion").value = descripcion;
                        document.getElementById("cantidad").value = cantidad; // Carga correctamente la cantidad
                    } else {
                        alert(data); // Si no se encuentra, muestra alerta
                        limpiarCampos(); // Limpia los campos
                    }
                })
                .catch(error => console.error('Error:', error));
            }
        }

        function limpiarCampos() {
            document.getElementById("nombre").value = ''; // Limpia el campo nombre
            document.getElementById("descripcion").value = '';
            document.getElementById("cantidad").value = '';
        }

        function updateForm() {
            const action = document.getElementById("action").value;
            limpiarCampos(); // Limpia campos al cambiar de opción

            if (action === "UPDATE") {
                document.getElementById("nombre").disabled = false;
                document.getElementById("buscarBtn").style.display = "inline";
            } else {
                document.getElementById("buscarBtn").style.display = "none";
            }
        }
    </script>
</head>
<body>
    <h2>Gestión de Inventario de Herramientas</h2>

    <form id="actionForm" method="post" action="inventario">
        <label for="action">Acción:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required><br><br>

        <label for="descripcion">Descripción:</label>
        <input type="text" id="descripcion" name="descripcion"><br><br>

        <label for="cantidad">Cantidad:</label>
        <input type="number" id="cantidad" name="cantidad"><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarHerramienta()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
