package Servlets;

import DAO.ProveedorDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/proveedor")
public class Proveedor extends HttpServlet {

    private ProveedorDAO proveedorDAO = new ProveedorDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action"); // INSERT, UPDATE, DELETE, SEARCH
        String nombre = request.getParameter("nombre");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        try {
            switch (action) {
                case "INSERT":
                    if (proveedorDAO.insertarProveedor(nombre, telefono, direccion)) {
                        response.getWriter().println("Proveedor registrado exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar el proveedor.");
                    }
                    break;

                case "UPDATE":
                    if (proveedorDAO.actualizarProveedor(nombre, telefono, direccion)) {
                        response.getWriter().println("Proveedor actualizado exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar el proveedor.");
                    }
                    break;

                case "DELETE":
                    if (proveedorDAO.eliminarProveedor(nombre)) {
                        response.getWriter().println("Proveedor eliminado.");
                    } else {
                        response.getWriter().println("Proveedor no encontrado.");
                    }
                    break;

                case "SEARCH":
                    List<String> datosProveedor = proveedorDAO.buscarProveedor(nombre);
                    if (datosProveedor.isEmpty()) {
                        response.getWriter().println("Proveedor no encontrado.");
                    } else {
                        response.getWriter().println(String.join(",", datosProveedor));
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
