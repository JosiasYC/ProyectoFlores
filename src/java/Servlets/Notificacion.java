package Servlets;

import DAO.NotificacionDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/notificacion")
public class Notificacion extends HttpServlet {

    private NotificacionDAO notificacionDAO = new NotificacionDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action"); // INSERT, UPDATE, DELETE, SEARCH
        String notificacionIdStr = request.getParameter("notificacion_id");
        String mensaje = request.getParameter("mensaje");
        String fechaStr = request.getParameter("fecha");
        String leidoStr = request.getParameter("leido");
        String usuarioIdStr = request.getParameter("usuario_id");

        try {
            switch (action) {
                case "INSERT":
                    if (notificacionDAO.insertarNotificacion(
                            mensaje, Date.valueOf(fechaStr), Boolean.parseBoolean(leidoStr), 
                            Integer.parseInt(usuarioIdStr))) {
                        response.getWriter().println("Notificación registrada exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar la notificación.");
                    }
                    break;

                case "UPDATE":
                    if (notificacionDAO.actualizarNotificacion(
                            Integer.parseInt(notificacionIdStr), mensaje, Boolean.parseBoolean(leidoStr))) {
                        response.getWriter().println("Notificación actualizada exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar la notificación.");
                    }
                    break;

                case "DELETE":
                    if (notificacionDAO.eliminarNotificacion(Integer.parseInt(notificacionIdStr))) {
                        response.getWriter().println("Notificación eliminada.");
                    } else {
                        response.getWriter().println("Notificación no encontrada.");
                    }
                    break;

                case "SEARCH":
                    List<Object> notificacionDatos = notificacionDAO.buscarNotificacion(Integer.parseInt(notificacionIdStr));
                    if (notificacionDatos.isEmpty()) {
                        response.getWriter().println("Notificación no encontrada.");
                    } else {
                        response.getWriter().println(String.join(",", 
                            notificacionDatos.stream().map(Object::toString).toArray(String[]::new)));
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
