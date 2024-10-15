package DAO;

import ConexionDB.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RolDAO {

    // Método para insertar un nuevo rol
    public void insertarRol(String nombre) throws SQLException {
        String sql = "INSERT INTO ROL (nombre) VALUES (?)";
        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
        }
    }

    // Método para actualizar un rol
    public void actualizarRol(int rolId, String nombre) throws SQLException {
        String sql = "UPDATE ROL SET nombre = ? WHERE rol_id = ?";
        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2, rolId);
            pstmt.executeUpdate();
        }
    }

    // Método para eliminar un rol
    public boolean eliminarRol(int rolId) throws SQLException {
        String sql = "DELETE FROM ROL WHERE rol_id = ?";
        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, rolId);
            return pstmt.executeUpdate() > 0; // Retorna true si se eliminó algún registro
        }
    }

    // Método para buscar un rol
    public String buscarRol(int rolId) throws SQLException {
        String sql = "SELECT nombre FROM ROL WHERE rol_id = ?";
        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, rolId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            } else {
                return "Rol no encontrado.";
            }
        }
    }
}
