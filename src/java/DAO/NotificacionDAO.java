package DAO;

import ConexionDB.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificacionDAO {

    private final Connection conn;

    public NotificacionDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // INSERTAR NOTIFICACIÓN
    public boolean insertarNotificacion(String mensaje, Date fecha, boolean leido, int usuarioId) throws SQLException {
        String sql = "INSERT INTO NOTIFICACION (mensaje, fecha, leido, usuario_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mensaje);
            stmt.setDate(2, fecha);
            stmt.setBoolean(3, leido);
            stmt.setInt(4, usuarioId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR NOTIFICACIÓN
    public boolean actualizarNotificacion(int notificacionId, String mensaje, boolean leido) throws SQLException {
        String sql = "UPDATE NOTIFICACION SET mensaje = ?, leido = ? WHERE notificacion_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mensaje);
            stmt.setBoolean(2, leido);
            stmt.setInt(3, notificacionId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ELIMINAR NOTIFICACIÓN
    public boolean eliminarNotificacion(int notificacionId) throws SQLException {
        String sql = "DELETE FROM NOTIFICACION WHERE notificacion_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificacionId);
            return stmt.executeUpdate() > 0;
        }
    }

    // BUSCAR NOTIFICACIÓN POR ID
    public List<Object> buscarNotificacion(int notificacionId) throws SQLException {
        String sql = "SELECT * FROM NOTIFICACION WHERE notificacion_id = ?";
        List<Object> notificacionDatos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificacionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                notificacionDatos.add(rs.getString("mensaje"));
                notificacionDatos.add(rs.getDate("fecha"));
                notificacionDatos.add(rs.getBoolean("leido"));
                notificacionDatos.add(rs.getInt("usuario_id"));
            }
        }
        return notificacionDatos;
    }
}
