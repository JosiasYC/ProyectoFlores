<html>
<head>
    <title>Gestión de Trabajadores</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container {
            margin-top: 10px;
        }
    </style>
    <script>
        function buscarTrabajador() {
            const nombre = document.getElementById("nombre").value;

            if (nombre.trim() !== "") {
                fetch(`trabajador`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', nombre: nombre })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Trabajador no encontrado.") {
                        const [cargo, horario, salario, fechaRegistro] = data.split(",");
                        document.getElementById("cargo").value = cargo;
                        document.getElementById("horario").value = horario;
                        document.getElementById("salario").value = salario;
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
            document.getElementById("cargo").value = '';
            document.getElementById("horario").value = '';
            document.getElementById("salario").value = '';
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
    <h2>Gestión de Trabajadores</h2>

    <form id="actionForm" method="post" action="trabajador">
        <label for="action">Acción:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required><br><br>

        <label for="cargo">Cargo:</label>
        <input type="text" id="cargo" name="cargo"><br><br>

        <label for="horario">Horario:</label>
        <input type="text" id="horario" name="horario"><br><br>

        <label for="salario">Salario:</label>
        <input type="text" id="salario" name="salario"><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarTrabajador()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
