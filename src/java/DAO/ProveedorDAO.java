package DAO;

import ConexionDB.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    private final Connection conn;

    // Constructor que obtiene la conexión única desde la clase Conexion
    public ProveedorDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // INSERTAR PROVEEDOR
    public boolean insertarProveedor(String nombre, String telefono, String direccion) throws SQLException {
        String sql = "INSERT INTO PROVEEDOR (nombre, telefono, direccion) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, telefono);
            stmt.setString(3, direccion);
            return stmt.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR PROVEEDOR
    public boolean actualizarProveedor(String nombre, String telefono, String direccion) throws SQLException {
        String sql = "UPDATE PROVEEDOR SET telefono = ?, direccion = ? WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, telefono);
            stmt.setString(2, direccion);
            stmt.setString(3, nombre);
            return stmt.executeUpdate() > 0;
        }
    }

    // ELIMINAR PROVEEDOR
    public boolean eliminarProveedor(String nombre) throws SQLException {
        String sql = "DELETE FROM PROVEEDOR WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            return stmt.executeUpdate() > 0;
        }
    }

    // BUSCAR PROVEEDOR POR NOMBRE
    public List<String> buscarProveedor(String nombre) throws SQLException {
        String sql = "SELECT telefono, direccion FROM PROVEEDOR WHERE nombre = ?";
        List<String> datosProveedor = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                datosProveedor.add(rs.getString("telefono"));
                datosProveedor.add(rs.getString("direccion"));
            }
        }
        return datosProveedor;
    }
}
