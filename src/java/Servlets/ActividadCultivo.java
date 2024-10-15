package Servlets;

import DAO.ActividadCultivoDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/actividadCultivo")
public class ActividadCultivo extends HttpServlet {

    private final ActividadCultivoDAO actividadCultivoDAO = new ActividadCultivoDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action"); // INSERT, UPDATE, DELETE, SEARCH
        String actividadIdStr = request.getParameter("actividad_id");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String fechaStr = request.getParameter("fecha");
        String productoIdStr = request.getParameter("producto_id");

        try {
            switch (action) {
                case "INSERT":
                    if (actividadCultivoDAO.insertarActividad(
                            nombre,
                            descripcion,
                            Date.valueOf(fechaStr),
                            Integer.parseInt(productoIdStr))) {
                        response.getWriter().println("Actividad de cultivo registrada exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar la actividad de cultivo.");
                    }
                    break;

                case "UPDATE":
                    if (actividadCultivoDAO.actualizarActividad(
                            Integer.parseInt(actividadIdStr),
                            nombre,
                            descripcion,
                            Date.valueOf(fechaStr),
                            Integer.parseInt(productoIdStr))) {
                        response.getWriter().println("Actividad de cultivo actualizada exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar la actividad de cultivo.");
                    }
                    break;

                case "DELETE":
                    if (actividadCultivoDAO.eliminarActividad(Integer.parseInt(actividadIdStr))) {
                        response.getWriter().println("Actividad de cultivo eliminada.");
                    } else {
                        response.getWriter().println("Actividad de cultivo no encontrada.");
                    }
                    break;

                case "SEARCH":
                    List<Object> actividadDatos = actividadCultivoDAO.buscarActividad(Integer.parseInt(actividadIdStr));
                    if (actividadDatos.isEmpty()) {
                        response.getWriter().println("Actividad de cultivo no encontrada.");
                    } else {
                        response.getWriter().println(String.join(",", 
                            actividadDatos.stream().map(Object::toString).toArray(String[]::new)));
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
