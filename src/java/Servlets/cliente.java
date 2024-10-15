package Servlets;

import DAO.ClienteDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cliente")
public class cliente extends HttpServlet {

    private ClienteDAO clienteDAO = new ClienteDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        try {
            switch (action) {
                case "INSERT":
                    if (clienteDAO.insertarCliente(nombre, email, telefono, direccion)) {
                        response.getWriter().println("Cliente registrado exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar el cliente.");
                    }
                    break;

                case "UPDATE":
                    if (clienteDAO.actualizarCliente(nombre, email, telefono, direccion)) {
                        response.getWriter().println("Cliente actualizado exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar el cliente.");
                    }
                    break;

                case "DELETE":
                    if (clienteDAO.eliminarCliente(nombre)) {
                        response.getWriter().println("Cliente eliminado.");
                    } else {
                        response.getWriter().println("Cliente no encontrado.");
                    }
                    break;

                case "SEARCH":
                    List<String> clienteDatos = clienteDAO.buscarCliente(nombre);
                    if (clienteDatos.isEmpty()) {
                        response.getWriter().println("Cliente no encontrado.");
                    } else {
                        response.getWriter().println(String.join(",", clienteDatos));
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
