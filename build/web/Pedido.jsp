<html>
<head>
    <title>Gestión de Pedidos</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container {
            margin-top: 10px;
        }
    </style>
    <script>
        function buscarPedido() {
            const pedidoId = document.getElementById("pedido_id").value;

            if (pedidoId.trim() !== "") {
                fetch(`pedido`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', pedido_id: pedidoId })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Pedido no encontrado.") {
                        const [cliente_id, fecha_pedido, estado, total] = data.split(",");
                        document.getElementById("cliente_id").value = cliente_id;
                        document.getElementById("fecha_pedido").value = fecha_pedido;
                        document.getElementById("estado").value = estado;
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
            document.getElementById("pedido_id").value = '';
            document.getElementById("cliente_id").value = '';
            document.getElementById("fecha_pedido").value = '';
            document.getElementById("estado").value = '';
            document.getElementById("total").value = '';
        }

        function updateForm() {
            const action = document.getElementById("action").value;
            limpiarCampos();

            if (action === "UPDATE" || action === "DELETE") {
                document.getElementById("pedido_id").disabled = false;
                document.getElementById("buscarBtn").style.display = "inline";
            } else {
                document.getElementById("buscarBtn").style.display = "none";
            }
        }
    </script>
</head>
<body>
    <h2>Gestión de Pedidos</h2>

    <form id="actionForm" method="post" action="pedido">
        <label for="action">Acción:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="pedido_id">ID del Pedido:</label>
        <input type="text" id="pedido_id" name="pedido_id" required><br><br>

        <label for="cliente_id">ID del Cliente:</label>
        <input type="text" id="cliente_id" name="cliente_id"><br><br>

        <label for="fecha_pedido">Fecha de Pedido:</label>
        <input type="date" id="fecha_pedido" name="fecha_pedido"><br><br>

        <label for="estado">Estado:</label>
        <input type="text" id="estado" name="estado"><br><br>

        <label for="total">Total:</label>
        <input type="text" id="total" name="total"><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarPedido()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
