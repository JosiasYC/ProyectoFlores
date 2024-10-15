package DAO;

import ConexionDB.Conexion;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private final Connection conn;

    public ProductoDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // INSERTAR PRODUCTO
    public boolean insertarProducto(String nombre, String descripcion, BigDecimal precio, 
                                    int stock, Date fechaCultivo, int proveedorId) throws SQLException {
        String sql = "INSERT INTO PRODUCTO (nombre, descripcion, precio, stock, fecha_cultivo, proveedor_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setBigDecimal(3, precio);
            stmt.setInt(4, stock);
            stmt.setDate(5, fechaCultivo);
            stmt.setInt(6, proveedorId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR PRODUCTO
    public boolean actualizarProducto(int productoId, String nombre, String descripcion, 
                                      BigDecimal precio, int stock, Date fechaCultivo, int proveedorId) throws SQLException {
        String sql = "UPDATE PRODUCTO SET nombre = ?, descripcion = ?, precio = ?, stock = ?, fecha_cultivo = ?, proveedor_id = ? WHERE producto_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setBigDecimal(3, precio);
            stmt.setInt(4, stock);
            stmt.setDate(5, fechaCultivo);
            stmt.setInt(6, proveedorId);
            stmt.setInt(7, productoId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ELIMINAR PRODUCTO
    public boolean eliminarProducto(int productoId) throws SQLException {
        String sql = "DELETE FROM PRODUCTO WHERE producto_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productoId);
            return stmt.executeUpdate() > 0;
        }
    }

    // BUSCAR PRODUCTO POR ID
    public List<Object> buscarProducto(int productoId) throws SQLException {
        String sql = "SELECT * FROM PRODUCTO WHERE producto_id = ?";
        List<Object> productoDatos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                productoDatos.add(rs.getString("nombre"));
                productoDatos.add(rs.getString("descripcion"));
                productoDatos.add(rs.getBigDecimal("precio"));
                productoDatos.add(rs.getInt("stock"));
                productoDatos.add(rs.getDate("fecha_cultivo"));
                productoDatos.add(rs.getInt("proveedor_id"));
            }
        }
        return productoDatos;
    }
}
