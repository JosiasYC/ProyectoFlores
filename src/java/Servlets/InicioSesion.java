package Servlets;

import DAO.InicioSesionDAO; // Cambiar a InicioSesionDAO
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class InicioSesion extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String nombreUsuario = request.getParameter("nombre_usuario");
        String password = request.getParameter("password_hash");

        // Utilizar el DAO para validar el usuario
        InicioSesionDAO inicioSesionDAO = new InicioSesionDAO(); // Cambiado
        boolean esValido = inicioSesionDAO.validarUsuario(nombreUsuario, password); // Usar InicioSesionDAO

        if (esValido) {
            // Las credenciales son válidas, redirigir a la página de inicio
            response.getWriter().println("Usted ingreso con exito!!!.");
        } else {
            // Las credenciales son incorrectas, mostrar mensaje de error
            response.getWriter().println("Usuario o contraseña incorrectos.");
        }
    }
}
