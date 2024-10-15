<html>
<head>
    <title>Gestión de Compras</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container { margin-top: 10px; }
    </style>
    <script>
        function buscarCompra() {
            const compraId = document.getElementById("compra_id").value;

            if (compraId.trim() !== "") {
                fetch(`compra`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', compra_id: compraId })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Compra no encontrada.") {
                        const [proveedor_id, fecha_compra, total] = data.split(",");
                        document.getElementById("proveedor_id").value = proveedor_id;
                        document.getElementById("fecha_compra").value = fecha_compra;
                        document.getElementById("total").value = total;
                    } else {
                        alert(data);
                        limpiarCampos();
                    }
                })
                .catch(error => console.error('Error:', error));
            }
        }

        function limpiarCampos() {
            document.getElementById("compra_id").value = '';
            document.getElementById("proveedor_id").value = '';
            document.getElementById("fecha_compra").value = '';
            document.getElementById("total").value = '';
        }

        function updateForm() {
            const action = document.getElementById("action").value;
            limpiarCampos();

            if (action === "UPDATE" || action === "DELETE") {
                document.getElementById("compra_id").disabled = false;
                document.getElementById("buscarBtn").style.display = "inline";
            } else {
                document.getElementById("buscarBtn").style.display = "none";
            }
        }
    </script>
</head>
<body>
    <h2>Gestión de Compras</h2>

    <form id="actionForm" method="post" action="compra">
        <label for="action">Acción:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="compra_id">ID de la Compra:</label>
        <input type="text" id="compra_id" name="compra_id" required><br><br>

        <label for="proveedor_id">ID del Proveedor:</label>
        <input type="text" id="proveedor_id" name="proveedor_id"><br><br>

        <label for="fecha_compra">Fecha de Compra:</label>
        <input type="date" id="fecha_compra" name="fecha_compra"><br><br>

        <label for="total">Total:</label>
        <input type="text" id="total" name="total"><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarCompra()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
