<html>
<head>
    <title>Gesti�n de Roles</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container {
            margin-top: 10px;
        }
    </style>
    <script>
        function buscarRol() {
            const rolId = document.getElementById("rol_id").value;

            if (rolId.trim() !== "") {
                fetch(`rol`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', rol_id: rolId })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Rol no encontrado.") {
                        document.getElementById("nombre").value = data; // Carga el nombre
                    } else {
                        alert(data); // Si no se encuentra, muestra alerta
                        limpiarCampos(); // Limpia los campos
                    }
                })
                .catch(error => console.error('Error:', error));
            }
        }

        function limpiarCampos() {
            document.getElementById("rol_id").value = ''; // Limpia el campo ID
            document.getElementById("nombre").value = ''; // Limpia el campo nombre
        }

        function updateForm() {
            const action = document.getElementById("action").value;
            limpiarCampos(); // Limpia campos al cambiar de opci�n

            if (action === "UPDATE" || action === "DELETE") {
                document.getElementById("rol_id").style.display = "inline"; // Muestra el campo
                document.getElementById("rol_id").disabled = false; // Habilita el campo ID
                document.getElementById("buscarBtn").style.display = "inline"; // Muestra bot�n de buscar
            } else {
                document.getElementById("rol_id").style.display = "none"; // Oculta el campo
                document.getElementById("rol_id").value = ''; // Limpia el campo ID
                document.getElementById("buscarBtn").style.display = "none"; // Oculta bot�n de buscar
            }
        }

        function validarFormulario() {
            const action = document.getElementById("action").value;
            const rolId = document.getElementById("rol_id").value;
            const nombre = document.getElementById("nombre").value;

            if ((action === "UPDATE" || action === "DELETE") && (rolId.trim() === "" || isNaN(rolId))) {
                alert("El ID de rol es requerido y debe ser un n�mero para las acciones de actualizaci�n y eliminaci�n.");
                return false;
            }

            if (action === "INSERT" && nombre.trim() === "") {
                alert("El nombre es requerido para insertar.");
                return false;
            }

            return true; // Si todo est� bien, permite el env�o del formulario
        }
    </script>
</head>
<body>
    <h2>Gesti�n de Roles</h2>

    <form id="actionForm" method="post" action="rol" onsubmit="return validarFormulario()">
        <label for="action">Acci�n:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="rol_id">ID de Rol:</label>
        <input type="text" id="rol_id" name="rol_id" style="display:none;"><br><br>

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarRol()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
