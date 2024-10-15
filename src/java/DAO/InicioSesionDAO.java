package DAO;

import ConexionDB.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InicioSesionDAO {

    public boolean validarUsuario(String nombreUsuario, String password) {
        boolean esValido = false;
        String sql = "SELECT * FROM USUARIO WHERE nombre_usuario = ? AND password_hash = ?";

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, password); // Asegúrate de usar un hash en la contraseña

            try (ResultSet rs = stmt.executeQuery()) {
                esValido = rs.next(); // Devuelve true si se encuentra un registro
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return esValido;
    }
}
