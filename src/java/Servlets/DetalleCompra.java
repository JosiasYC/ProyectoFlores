package Servlets;

import DAO.DetalleCompraDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/detalle_compra")
public class DetalleCompra extends HttpServlet {

    private DetalleCompraDAO detalleCompraDAO = new DetalleCompraDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action"); // INSERT, UPDATE, DELETE, SEARCH
        String detalleIdStr = request.getParameter("detalle_id");
        String compraIdStr = request.getParameter("compra_id");
        String productoIdStr = request.getParameter("producto_id");
        String cantidadStr = request.getParameter("cantidad");
        String precioUnitarioStr = request.getParameter("precio_unitario");

        try {
            switch (action) {
                case "INSERT":
                    if (detalleCompraDAO.insertarDetalleCompra(
                            Integer.parseInt(compraIdStr),
                            Integer.parseInt(productoIdStr),
                            Integer.parseInt(cantidadStr),
                            new BigDecimal(precioUnitarioStr))) {
                        response.getWriter().println("Detalle de compra registrado exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar el detalle de compra.");
                    }
                    break;

                case "UPDATE":
                    if (detalleCompraDAO.actualizarDetalleCompra(
                            Integer.parseInt(detalleIdStr),
                            Integer.parseInt(cantidadStr),
                            new BigDecimal(precioUnitarioStr))) {
                        response.getWriter().println("Detalle de compra actualizado exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar el detalle de compra.");
                    }
                    break;

                case "DELETE":
                    if (detalleCompraDAO.eliminarDetalleCompra(Integer.parseInt(detalleIdStr))) {
                        response.getWriter().println("Detalle de compra eliminado.");
                    } else {
                        response.getWriter().println("Detalle no encontrado.");
                    }
                    break;

                case "SEARCH":
                    List<Object> detalleDatos = detalleCompraDAO.buscarDetalleCompra(Integer.parseInt(detalleIdStr));
                    if (detalleDatos.isEmpty()) {
                        response.getWriter().println("Detalle no encontrado.");
                    } else {
                        response.getWriter().println(String.join(",", 
                            detalleDatos.stream().map(Object::toString).toArray(String[]::new)));
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
