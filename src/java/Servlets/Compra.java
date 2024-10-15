package Servlets;

import DAO.CompraDAO;
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

@WebServlet("/compra")
public class Compra extends HttpServlet {

    private final CompraDAO compraDAO = new CompraDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action"); // INSERT, UPDATE, DELETE, SEARCH
        String compraIdStr = request.getParameter("compra_id");
        String proveedorIdStr = request.getParameter("proveedor_id");
        String fechaCompraStr = request.getParameter("fecha_compra");
        String totalStr = request.getParameter("total");

        try {
            switch (action) {
                case "INSERT":
                    if (compraDAO.insertarCompra(
                            Integer.parseInt(proveedorIdStr),
                            Date.valueOf(fechaCompraStr),
                            new BigDecimal(totalStr))) {
                        response.getWriter().println("Compra registrada exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar la compra.");
                    }
                    break;

                case "UPDATE":
                    if (compraDAO.actualizarCompra(
                            Integer.parseInt(compraIdStr),
                            Integer.parseInt(proveedorIdStr),
                            Date.valueOf(fechaCompraStr),
                            new BigDecimal(totalStr))) {
                        response.getWriter().println("Compra actualizada exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar la compra.");
                    }
                    break;

                case "DELETE":
                    if (compraDAO.eliminarCompra(Integer.parseInt(compraIdStr))) {
                        response.getWriter().println("Compra eliminada.");
                    } else {
                        response.getWriter().println("Compra no encontrada.");
                    }
                    break;

                case "SEARCH":
                    List<Object> compraDatos = compraDAO.buscarCompra(Integer.parseInt(compraIdStr));
                    if (compraDatos.isEmpty()) {
                        response.getWriter().println("Compra no encontrada.");
                    } else {
                        response.getWriter().println(String.join(",", 
                            compraDatos.stream().map(Object::toString).toArray(String[]::new)));
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
