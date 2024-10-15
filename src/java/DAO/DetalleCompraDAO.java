package DAO;

import ConexionDB.Conexion;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleCompraDAO {

    private final Connection conn;

    public DetalleCompraDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // INSERTAR DETALLE DE COMPRA
    public boolean insertarDetalleCompra(int compraId, int productoId, int cantidad, BigDecimal precioUnitario) throws SQLException {
        String sql = "INSERT INTO DETALLE_COMPRA (compra_id, producto_id, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, compraId);
            stmt.setInt(2, productoId);
            stmt.setInt(3, cantidad);
            stmt.setBigDecimal(4, precioUnitario);
            return stmt.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR DETALLE DE COMPRA
    public boolean actualizarDetalleCompra(int detalleId, int cantidad, BigDecimal precioUnitario) throws SQLException {
        String sql = "UPDATE DETALLE_COMPRA SET cantidad = ?, precio_unitario = ? WHERE detalle_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setBigDecimal(2, precioUnitario);
            stmt.setInt(3, detalleId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ELIMINAR DETALLE DE COMPRA
    public boolean eliminarDetalleCompra(int detalleId) throws SQLException {
        String sql = "DELETE FROM DETALLE_COMPRA WHERE detalle_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, detalleId);
            return stmt.executeUpdate() > 0;
        }
    }

    // BUSCAR DETALLE DE COMPRA
    public List<Object> buscarDetalleCompra(int detalleId) throws SQLException {
        String sql = "SELECT * FROM DETALLE_COMPRA WHERE detalle_id = ?";
        List<Object> detalleDatos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, detalleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                detalleDatos.add(rs.getInt("compra_id"));
                detalleDatos.add(rs.getInt("producto_id"));
                detalleDatos.add(rs.getInt("cantidad"));
                detalleDatos.add(rs.getBigDecimal("precio_unitario"));
                detalleDatos.add(rs.getBigDecimal("subtotal"));
            }
        }
        return detalleDatos;
    }
}
