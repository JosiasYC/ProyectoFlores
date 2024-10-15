package Servlets;

import DAO.DetallePedidoDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/detallePedido")
public class DetallePedido extends HttpServlet {

    private DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action"); // INSERT, UPDATE, DELETE, SEARCH
        String detalleIdStr = request.getParameter("detalle_id");
        String pedidoIdStr = request.getParameter("pedido_id");
        String productoIdStr = request.getParameter("producto_id");
        String cantidadStr = request.getParameter("cantidad");
        String precioUnitarioStr = request.getParameter("precio_unitario");

        try {
            switch (action) {
                case "INSERT":
                    if (detallePedidoDAO.insertarDetallePedido(
                            Integer.parseInt(pedidoIdStr),
                            Integer.parseInt(productoIdStr),
                            Integer.parseInt(cantidadStr),
                            new BigDecimal(precioUnitarioStr))) {
                        response.getWriter().println("Detalle de pedido registrado exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar el detalle de pedido.");
                    }
                    break;

                case "UPDATE":
                    if (detallePedidoDAO.actualizarDetallePedido(
                            Integer.parseInt(detalleIdStr),
                            Integer.parseInt(cantidadStr),
                            new BigDecimal(precioUnitarioStr))) {
                        response.getWriter().println("Detalle de pedido actualizado exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar el detalle de pedido.");
                    }
                    break;

                case "DELETE":
                    if (detallePedidoDAO.eliminarDetallePedido(Integer.parseInt(detalleIdStr))) {
                        response.getWriter().println("Detalle de pedido eliminado.");
                    } else {
                        response.getWriter().println("Detalle no encontrado.");
                    }
                    break;

                case "SEARCH":
                    List<Object> detalleDatos = detallePedidoDAO.buscarDetallePedido(Integer.parseInt(detalleIdStr));
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
