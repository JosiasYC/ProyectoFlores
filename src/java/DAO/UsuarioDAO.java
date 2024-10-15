package DAO;

import ConexionDB.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final Connection conn;

    // Constructor que obtiene la conexión única desde la clase Conexion
    public UsuarioDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // INSERTAR USUARIO
    public boolean insertarUsuario(String nombreUsuario, String email, String passwordHash, int rolId, 
                                   Integer trabajadorId, Integer clienteId) throws SQLException {
        String sql = "INSERT INTO USUARIO (nombre_usuario, email, password_hash, rol_id, trabajador_id, cliente_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, email);
            stmt.setString(3, passwordHash);
            stmt.setInt(4, rolId);
            stmt.setObject(5, trabajadorId);
            stmt.setObject(6, clienteId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR USUARIO
    public boolean actualizarUsuario(int usuarioId, String nombreUsuario, String email, String passwordHash, 
                                     int rolId, Integer trabajadorId, Integer clienteId) throws SQLException {
        String sql = "UPDATE USUARIO SET nombre_usuario = ?, email = ?, password_hash = ?, rol_id = ?, trabajador_id = ?, cliente_id = ? WHERE usuario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, email);
            stmt.setString(3, passwordHash);
            stmt.setInt(4, rolId);
            stmt.setObject(5, trabajadorId);
            stmt.setObject(6, clienteId);
            stmt.setInt(7, usuarioId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ELIMINAR USUARIO
    public boolean eliminarUsuario(int usuarioId) throws SQLException {
        String sql = "DELETE FROM USUARIO WHERE usuario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            return stmt.executeUpdate() > 0;
        }
    }

    // BUSCAR USUARIO POR ID
    public List<String> buscarUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT nombre_usuario, email, password_hash, rol_id, trabajador_id, cliente_id FROM USUARIO WHERE usuario_id = ?";
        List<String> datosUsuario = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                datosUsuario.add(rs.getString("nombre_usuario"));
                datosUsuario.add(rs.getString("email"));
                datosUsuario.add(rs.getString("password_hash"));
                datosUsuario.add(String.valueOf(rs.getInt("rol_id")));
                datosUsuario.add(String.valueOf(rs.getObject("trabajador_id")));
                datosUsuario.add(String.valueOf(rs.getObject("cliente_id")));
            }
        }
        return datosUsuario;
    }
}
