package Servlets;

import DAO.PedidoDAO;
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

@WebServlet("/pedido")
public class Pedido extends HttpServlet {

    private PedidoDAO pedidoDAO = new PedidoDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action"); // INSERT, UPDATE, DELETE, SEARCH
        String pedidoIdStr = request.getParameter("pedido_id");
        String clienteIdStr = request.getParameter("cliente_id");
        String fechaPedido = request.getParameter("fecha_pedido");
        String estado = request.getParameter("estado");
        String totalStr = request.getParameter("total");

        try {
            switch (action) {
                case "INSERT":
                    if (pedidoDAO.insertarPedido(
                            Integer.parseInt(clienteIdStr), Date.valueOf(fechaPedido), 
                            estado, new BigDecimal(totalStr))) {
                        response.getWriter().println("Pedido registrado exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar el pedido.");
                    }
                    break;

                case "UPDATE":
                    if (pedidoDAO.actualizarPedido(
                            Integer.parseInt(pedidoIdStr), Integer.parseInt(clienteIdStr), 
                            Date.valueOf(fechaPedido), estado, new BigDecimal(totalStr))) {
                        response.getWriter().println("Pedido actualizado exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar el pedido.");
                    }
                    break;

                case "DELETE":
                    if (pedidoDAO.eliminarPedido(Integer.parseInt(pedidoIdStr))) {
                        response.getWriter().println("Pedido eliminado.");
                    } else {
                        response.getWriter().println("Pedido no encontrado.");
                    }
                    break;

                case "SEARCH":
                    List<Object> pedidoDatos = pedidoDAO.buscarPedido(Integer.parseInt(pedidoIdStr));
                    if (pedidoDatos.isEmpty()) {
                        response.getWriter().println("Pedido no encontrado.");
                    } else {
                        response.getWriter().println(String.join(",", 
                            pedidoDatos.stream().map(Object::toString).toArray(String[]::new)));
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
