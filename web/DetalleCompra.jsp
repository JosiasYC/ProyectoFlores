<html>
<head>
    <title>Gestión de Detalles de Compra</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container { margin-top: 10px; }
    </style>
    <script>
        function buscarDetalle() {
            const detalleId = document.getElementById("detalle_id").value;

            if (detalleId.trim() !== "") {
                fetch(`detalle_compra`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', detalle_id: detalleId })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Detalle no encontrado.") {
                        const [compra_id, producto_id, cantidad, precio_unitario, subtotal] = data.split(",");
                        document.getElementById("compra_id").value = compra_id;
                        document.getElementById("producto_id").value = producto_id;
                        document.getElementById("cantidad").value = cantidad;
                        document.getElementById("precio_unitario").value = precio_unitario;
                    } else {
                        alert(data);
                        limpiarCampos();
                    }
                })
                .catch(error => console.error('Error:', error));
            }
        }

        function limpiarCampos() {
            document.getElementById("detalle_id").value = '';
            document.getElementById("compra_id").value = '';
            document.getElementById("producto_id").value = '';
            document.getElementById("cantidad").value = '';
            document.getElementById("precio_unitario").value = '';
        }

        function updateForm() {
            const action = document.getElementById("action").value;
            limpiarCampos();

            if (action === "UPDATE" || action === "DELETE") {
                document.getElementById("detalle_id").disabled = false;
                document.getElementById("buscarBtn").style.display = "inline";
            } else {
                document.getElementById("buscarBtn").style.display = "none";
            }
        }
    </script>
</head>
<body>
    <h2>Gestión de Detalles de Compra</h2>

    <form id="actionForm" method="post" action="detalle_compra">
        <label for="action">Acción:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="detalle_id">ID del Detalle:</label>
        <input type="text" id="detalle_id" name="detalle_id" required><br><br>

        <label for="compra_id">ID de la Compra:</label>
        <input type="text" id="compra_id" name="compra_id"><br><br>

        <label for="producto_id">ID del Producto:</label>
        <input type="text" id="producto_id" name="producto_id"><br><br>

        <label for="cantidad">Cantidad:</label>
        <input type="number" id="cantidad" name="cantidad"><br><br>

        <label for="precio_unitario">Precio Unitario:</label>
        <input type="text" id="precio_unitario" name="precio_unitario"><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarDetalle()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
