<html>
<head>
    <title>Gestión de Proveedores</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container {
            margin-top: 10px;
        }
    </style>
    <script>
        function buscarProveedor() {
            const nombre = document.getElementById("nombre").value;

            if (nombre.trim() !== "") {
                fetch(`proveedor`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', nombre: nombre })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Proveedor no encontrado.") {
                        const [telefono, direccion] = data.split(",");
                        document.getElementById("telefono").value = telefono;
                        document.getElementById("direccion").value = direccion;
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
            document.getElementById("telefono").value = '';
            document.getElementById("direccion").value = '';
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
    <h2>Gestión de Proveedores</h2>

    <form id="actionForm" method="post" action="proveedor">
        <label for="action">Acción:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required><br><br>

        <label for="telefono">Teléfono:</label>
        <input type="text" id="telefono" name="telefono"><br><br>

        <label for="direccion">Dirección:</label>
        <input type="text" id="direccion" name="direccion"><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarProveedor()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
