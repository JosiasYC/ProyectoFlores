package DAO;

import ConexionDB.Conexion;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    private final Connection conn;

    public CompraDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // INSERTAR COMPRA
    public boolean insertarCompra(int proveedorId, Date fechaCompra, BigDecimal total) throws SQLException {
        String sql = "INSERT INTO COMPRA (proveedor_id, fecha_compra, total) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, proveedorId);
            stmt.setDate(2, fechaCompra);
            stmt.setBigDecimal(3, total);
            return stmt.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR COMPRA
    public boolean actualizarCompra(int compraId, int proveedorId, Date fechaCompra, BigDecimal total) throws SQLException {
        String sql = "UPDATE COMPRA SET proveedor_id = ?, fecha_compra = ?, total = ? WHERE compra_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, proveedorId);
            stmt.setDate(2, fechaCompra);
            stmt.setBigDecimal(3, total);
            stmt.setInt(4, compraId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ELIMINAR COMPRA
    public boolean eliminarCompra(int compraId) throws SQLException {
        String sql = "DELETE FROM COMPRA WHERE compra_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, compraId);
            return stmt.executeUpdate() > 0;
        }
    }

    // BUSCAR COMPRA
    public List<Object> buscarCompra(int compraId) throws SQLException {
        String sql = "SELECT * FROM COMPRA WHERE compra_id = ?";
        List<Object> compraDatos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, compraId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                compraDatos.add(rs.getInt("proveedor_id"));
                compraDatos.add(rs.getDate("fecha_compra"));
                compraDatos.add(rs.getBigDecimal("total"));
            }
        }
        return compraDatos;
    }
}
