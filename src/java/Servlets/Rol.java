package Servlets;

import DAO.RolDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rol")
public class Rol extends HttpServlet {

    private RolDAO rolDAO = new RolDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action"); // INSERT, UPDATE, DELETE, SEARCH
        String rolIdStr = request.getParameter("rol_id"); // Solo para UPDATE y DELETE
        String nombre = request.getParameter("nombre");

        try {
            switch (action) {
                case "INSERT":
                    rolDAO.insertarRol(nombre);
                    response.getWriter().println("Rol registrado exitosamente.");
                    break;

                case "UPDATE":
                    if (rolIdStr == null || rolIdStr.isEmpty()) {
                        response.getWriter().println("El ID de rol es requerido para actualizar.");
                        return;
                    }
                    rolDAO.actualizarRol(Integer.parseInt(rolIdStr), nombre);
                    response.getWriter().println("Rol actualizado exitosamente.");
                    break;

                case "DELETE":
                    if (rolIdStr == null || rolIdStr.isEmpty()) {
                        response.getWriter().println("El ID de rol es requerido para eliminar.");
                        return;
                    }
                    boolean eliminado = rolDAO.eliminarRol(Integer.parseInt(rolIdStr));
                    response.getWriter().println(eliminado ? "Rol eliminado." : "Rol no encontrado.");
                    break;

                case "SEARCH":
                    if (rolIdStr == null || rolIdStr.isEmpty()) {
                        response.getWriter().println("El ID de rol es requerido para buscar.");
                        return;
                    }
                    String rolNombre = rolDAO.buscarRol(Integer.parseInt(rolIdStr));
                    response.getWriter().println(rolNombre);
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
