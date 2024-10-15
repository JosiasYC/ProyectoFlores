package Servlets;

import DAO.ProductoDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/producto")
public class Producto extends HttpServlet {

    private ProductoDAO productoDAO = new ProductoDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action"); // INSERT, UPDATE, DELETE, SEARCH
        String productoIdStr = request.getParameter("producto_id");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precioStr = request.getParameter("precio");
        String stockStr = request.getParameter("stock");
        String fechaCultivo = request.getParameter("fecha_cultivo");
        String proveedorIdStr = request.getParameter("proveedor_id");

        try {
            switch (action) {
                case "INSERT":
                    if (productoDAO.insertarProducto(
                            nombre, descripcion, new BigDecimal(precioStr), 
                            Integer.parseInt(stockStr), Date.valueOf(fechaCultivo), 
                            Integer.parseInt(proveedorIdStr))) {
                        response.getWriter().println("Producto registrado exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar el producto.");
                    }
                    break;

                case "UPDATE":
                    if (productoDAO.actualizarProducto(
                            Integer.parseInt(productoIdStr), nombre, descripcion, 
                            new BigDecimal(precioStr), Integer.parseInt(stockStr), 
                            Date.valueOf(fechaCultivo), Integer.parseInt(proveedorIdStr))) {
                        response.getWriter().println("Producto actualizado exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar el producto.");
                    }
                    break;

                case "DELETE":
                    if (productoDAO.eliminarProducto(Integer.parseInt(productoIdStr))) {
                        response.getWriter().println("Producto eliminado.");
                    } else {
                        response.getWriter().println("Producto no encontrado.");
                    }
                    break;

                case "SEARCH":
                    List<Object> productoDatos = productoDAO.buscarProducto(Integer.parseInt(productoIdStr));
                    if (productoDatos.isEmpty()) {
                        response.getWriter().println("Producto no encontrado.");
                    } else {
                        response.getWriter().println(String.join(",", 
                            productoDatos.stream().map(Object::toString).toArray(String[]::new)));
                    }
                    break;

                default:
                    response.getWriter().println("Acción no válida.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error en la base de datos: " + e.getMessage());
        }
    }
}
