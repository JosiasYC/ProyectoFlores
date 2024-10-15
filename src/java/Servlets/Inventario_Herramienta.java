package Servlets;

import DAO.InventarioHerramientaDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/inventario")
public class Inventario_Herramienta extends HttpServlet {

    private InventarioHerramientaDAO inventarioDAO = new InventarioHerramientaDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action"); // INSERT, UPDATE, DELETE, SEARCH
        String herramientaIdStr = request.getParameter("herramienta_id");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String cantidadStr = request.getParameter("cantidad");

        try {
            switch (action) {
                case "INSERT":
                    if (inventarioDAO.insertarHerramienta(nombre, descripcion, Integer.parseInt(cantidadStr))) {
                        response.getWriter().println("Herramienta registrada exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar la herramienta.");
                    }
                    break;

                case "UPDATE":
                    if (inventarioDAO.actualizarHerramienta(nombre, descripcion, Integer.parseInt(cantidadStr))) {
                        response.getWriter().println("Herramienta actualizada exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar la herramienta.");
                    }
                    break;

                case "DELETE":
                    if (inventarioDAO.eliminarHerramienta(nombre)) {
                        response.getWriter().println("Herramienta eliminada.");
                    } else {
                        response.getWriter().println("Herramienta no encontrada.");
                    }
                    break;

                case "SEARCH":
                    List<Object> herramientaDatos = inventarioDAO.buscarHerramienta(nombre);
                    if (herramientaDatos.isEmpty()) {
                        response.getWriter().println("Herramienta no encontrada.");
                    } else {
                        response.getWriter().println(String.join(",", 
                            herramientaDatos.stream().map(Object::toString).toArray(String[]::new)));
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
