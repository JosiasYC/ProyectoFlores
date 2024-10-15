package Servlets;

import DAO.TrabajadorDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/trabajador")
public class Trabajador extends HttpServlet {

    private TrabajadorDAO trabajadorDAO = new TrabajadorDAO(); // Instancia del DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String nombre = request.getParameter("nombre");
        String cargo = request.getParameter("cargo");
        String horario = request.getParameter("horario");
        String salarioStr = request.getParameter("salario");

        try {
            switch (action) {
                case "INSERT":
                    BigDecimal salarioInsert = new BigDecimal(salarioStr);
                    if (trabajadorDAO.insertarTrabajador(nombre, cargo, horario, salarioInsert)) {
                        response.getWriter().println("Trabajador registrado exitosamente.");
                    } else {
                        response.getWriter().println("Error al registrar el trabajador.");
                    }
                    break;

                case "UPDATE":
                    BigDecimal salarioUpdate = new BigDecimal(salarioStr);
                    if (trabajadorDAO.actualizarTrabajador(nombre, cargo, horario, salarioUpdate)) {
                        response.getWriter().println("Trabajador actualizado exitosamente.");
                    } else {
                        response.getWriter().println("Error al actualizar el trabajador.");
                    }
                    break;

                case "DELETE":
                    if (trabajadorDAO.eliminarTrabajador(nombre)) {
                        response.getWriter().println("Trabajador eliminado.");
                    } else {
                        response.getWriter().println("Trabajador no encontrado.");
                    }
                    break;

                case "SEARCH":
                    List<String> trabajadorDatos = trabajadorDAO.buscarTrabajador(nombre);
                    if (trabajadorDatos.isEmpty()) {
                        response.getWriter().println("Trabajador no encontrado.");
                    } else {
                        response.getWriter().println(String.join(",", trabajadorDatos));
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
