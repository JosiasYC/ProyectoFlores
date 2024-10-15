package DAO;

import ConexionDB.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadCultivoDAO {

    private final Connection conn;

    public ActividadCultivoDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // INSERTAR ACTIVIDAD CULTIVO
    public boolean insertarActividad(String nombre, String descripcion, Date fecha, int productoId) throws SQLException {
        String sql = "INSERT INTO ACTIVIDAD_CULTIVO (nombre, descripcion, fecha, producto_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDate(3, fecha);
            stmt.setInt(4, productoId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR ACTIVIDAD CULTIVO
    public boolean actualizarActividad(int actividadId, String nombre, String descripcion, Date fecha, int productoId) throws SQLException {
        String sql = "UPDATE ACTIVIDAD_CULTIVO SET nombre = ?, descripcion = ?, fecha = ?, producto_id = ? WHERE actividad_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDate(3, fecha);
            stmt.setInt(4, productoId);
            stmt.setInt(5, actividadId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ELIMINAR ACTIVIDAD CULTIVO
    public boolean eliminarActividad(int actividadId) throws SQLException {
        String sql = "DELETE FROM ACTIVIDAD_CULTIVO WHERE actividad_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, actividadId);
            return stmt.executeUpdate() > 0;
        }
    }

    // BUSCAR ACTIVIDAD CULTIVO
    public List<Object> buscarActividad(int actividadId) throws SQLException {
        String sql = "SELECT * FROM ACTIVIDAD_CULTIVO WHERE actividad_id = ?";
        List<Object> actividadDatos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, actividadId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                actividadDatos.add(rs.getString("nombre"));
                actividadDatos.add(rs.getString("descripcion"));
                actividadDatos.add(rs.getDate("fecha"));
                actividadDatos.add(rs.getInt("producto_id"));
            }
        }
        return actividadDatos;
    }
}
