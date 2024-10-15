package DAO;

import ConexionDB.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioHerramientaDAO {

    private final Connection conn;

    public InventarioHerramientaDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // INSERTAR HERRAMIENTA
    public boolean insertarHerramienta(String nombre, String descripcion, int cantidad) throws SQLException {
        String sql = "INSERT INTO INVENTARIO_HERRAMIENTA (nombre, descripcion, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setInt(3, cantidad);
            return stmt.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR HERRAMIENTA
    public boolean actualizarHerramienta(String nombre, String descripcion, int cantidad) throws SQLException {
        String sql = "UPDATE INVENTARIO_HERRAMIENTA SET descripcion = ?, cantidad = ? WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descripcion);
            stmt.setInt(2, cantidad);
            stmt.setString(3, nombre);
            return stmt.executeUpdate() > 0;
        }
    }

    // ELIMINAR HERRAMIENTA
    public boolean eliminarHerramienta(String nombre) throws SQLException {
        String sql = "DELETE FROM INVENTARIO_HERRAMIENTA WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            return stmt.executeUpdate() > 0;
        }
    }

    // BUSCAR HERRAMIENTA POR NOMBRE
    public List<Object> buscarHerramienta(String nombre) throws SQLException {
        String sql = "SELECT * FROM INVENTARIO_HERRAMIENTA WHERE nombre = ?";
        List<Object> herramientaDatos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                herramientaDatos.add(rs.getString("descripcion"));
                herramientaDatos.add(rs.getInt("cantidad"));
            }
        }
        return herramientaDatos;
    }
}
