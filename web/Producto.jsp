<html>
<head>
    <title>Gestión de Productos</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { margin-bottom: 20px; }
        .button-container {
            margin-top: 10px;
        }
    </style>
    <script>
        function buscarProducto() {
            const productoId = document.getElementById("producto_id").value;

            if (productoId.trim() !== "") {
                fetch(`producto`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ action: 'SEARCH', producto_id: productoId })
                })
                .then(response => response.text())
                .then(data => {
                    if (data !== "Producto no encontrado.") {
                        const [nombre, descripcion, precio, stock, fecha_cultivo, proveedor_id] = data.split(",");
                        document.getElementById("nombre").value = nombre;
                        document.getElementById("descripcion").value = descripcion;
                        document.getElementById("precio").value = precio;
                        document.getElementById("stock").value = stock;
                        document.getElementById("fecha_cultivo").value = fecha_cultivo;
                        document.getElementById("proveedor_id").value = proveedor_id;
                    } else {
                        alert(data);
                        limpiarCampos();
                    }
                })
                .catch(error => console.error('Error:', error));
            }
        }

        function limpiarCampos() {
            document.getElementById("producto_id").value = '';
            document.getElementById("nombre").value = '';
            document.getElementById("descripcion").value = '';
            document.getElementById("precio").value = '';
            document.getElementById("stock").value = '';
            document.getElementById("fecha_cultivo").value = '';
            document.getElementById("proveedor_id").value = '';
        }

        function updateForm() {
            const action = document.getElementById("action").value;
            limpiarCampos();

            if (action === "UPDATE" || action === "DELETE") {
                document.getElementById("producto_id").disabled = false;
                document.getElementById("buscarBtn").style.display = "inline";
            } else {
                document.getElementById("buscarBtn").style.display = "none";
            }
        }
    </script>
</head>
<body>
    <h2>Gestión de Productos</h2>

    <form id="actionForm" method="post" action="producto">
        <label for="action">Acción:</label>
        <select id="action" name="action" onchange="updateForm()">
            <option value="INSERT">Insertar</option>
            <option value="UPDATE">Actualizar</option>
            <option value="DELETE">Eliminar</option>
        </select><br><br>

        <label for="producto_id">ID del Producto:</label>
        <input type="text" id="producto_id" name="producto_id" required><br><br>

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre"><br><br>

        <label for="descripcion">Descripción:</label>
        <input type="text" id="descripcion" name="descripcion"><br><br>

        <label for="precio">Precio:</label>
        <input type="text" id="precio" name="precio"><br><br>

        <label for="stock">Stock:</label>
        <input type="text" id="stock" name="stock"><br><br>

        <label for="fecha_cultivo">Fecha de Cultivo:</label>
        <input type="date" id="fecha_cultivo" name="fecha_cultivo"><br><br>

        <label for="proveedor_id">ID del Proveedor:</label>
        <input type="text" id="proveedor_id" name="proveedor_id"><br><br>

        <div class="button-container">
            <button type="button" id="buscarBtn" style="display:none;" onclick="buscarProducto()">Buscar</button>
            <input type="submit" value="Ejecutar">
        </div>
    </form>
</body>
</html>
