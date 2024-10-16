<html>
<head>
    <title>Gesti�n de Clientes</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container {
            margin-top: 10px;
        }
    </style>
    <script>
        function buscarCliente() {
            const nombre = document.getElementById("nombre").value;

            if (nombre.trim() !== "") {
                fetch(`cliente`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', nombre: nombre })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Cliente no encontrado.") {
                        const [email, telefono, direccion] = data.split(",");
                        document.getElementById("email").value = email;
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
            document.getElementById("email").value = '';
            document.getElementById("telefono").value = '';
            document.getElementById("direccion").value = '';
        }

        function updateForm() {
            const action = document.getElementById("action").value;
            limpiarCampos(); // Limpia campos al cambiar de opci�n

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
    <h2>Gesti�n de Clientes</h2>

    <form id="actionForm" method="post" action="cliente">
        <label for="action">Acci�n:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required><br><br>

        <label for="email">Email:</label>
        <input type="text" id="email" name="email"><br><br>

        <label for="telefono">Tel�fono:</label>
        <input type="text" id="telefono" name="telefono"><br><br>

        <label for="direccion">Direcci�n:</label>
        <input type="text" id="direccion" name="direccion"><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarCliente()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
