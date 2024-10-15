package Servlets;

import DAO.UsuarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/usuario")
public class Usuario extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String usuarioIdStr = request.getParameter("usuario_id");
        String nombreUsuario = request.getParameter("nombre_usuario");
        String email = request.getParameter("email");
        String passwordHash = request.getParameter("password_hash");
        String rolIdStr = request.getParameter("rol_id");
        String trabajadorIdStr = request.getParameter("trabajador_id");
        String clienteIdStr = request.getParameter("cliente_id");

        try {
            switch (action) {
                case "INSERT":
                    if (usuarioDAO.insertarUsuario(
                            nombreUsuario, email, passwordHash, 
                            Integer.parseInt(rolIdStr), 
                            trabajadorIdStr.isEmpty() ? null : Integer.parseInt(trabajadorIdStr), 
                            clienteIdStr.isEmpty() ? null : Integer.parseInt(clienteIdStr))) {
                        response.getWriter().println("Usuario registrado exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar el usuario.");
                    }
                    break;

                case "UPDATE":
                    if (usuarioDAO.actualizarUsuario(
                            Integer.parseInt(usuarioIdStr), nombreUsuario, email, passwordHash, 
                            Integer.parseInt(rolIdStr), 
                            trabajadorIdStr.isEmpty() ? null : Integer.parseInt(trabajadorIdStr), 
                            clienteIdStr.isEmpty() ? null : Integer.parseInt(clienteIdStr))) {
                        response.getWriter().println("Usuario actualizado exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar el usuario.");
                    }
                    break;

                case "DELETE":
                    if (usuarioDAO.eliminarUsuario(Integer.parseInt(usuarioIdStr))) {
                        response.getWriter().println("Usuario eliminado.");
                    } else {
                        response.getWriter().println("Usuario no encontrado.");
                    }
                    break;

                case "SEARCH":
                    List<String> datosUsuario = usuarioDAO.buscarUsuario(Integer.parseInt(usuarioIdStr));
                    if (datosUsuario.isEmpty()) {
                        response.getWriter().println("Usuario no encontrado.");
                    } else {
                        response.getWriter().println(String.join(",", datosUsuario));
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
