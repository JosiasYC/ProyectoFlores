package DAO;

import ConexionDB.Conexion;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    private final Connection conn;

    public PedidoDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // INSERTAR PEDIDO
    public boolean insertarPedido(int clienteId, Date fechaPedido, String estado, BigDecimal total) throws SQLException {
        String sql = "INSERT INTO PEDIDO (cliente_id, fecha_pedido, estado, total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setDate(2, fechaPedido);
            stmt.setString(3, estado);
            stmt.setBigDecimal(4, total);
            return stmt.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR PEDIDO
    public boolean actualizarPedido(int pedidoId, int clienteId, Date fechaPedido, String estado, BigDecimal total) throws SQLException {
        String sql = "UPDATE PEDIDO SET cliente_id = ?, fecha_pedido = ?, estado = ?, total = ? WHERE pedido_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setDate(2, fechaPedido);
            stmt.setString(3, estado);
            stmt.setBigDecimal(4, total);
            stmt.setInt(5, pedidoId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ELIMINAR PEDIDO
    public boolean eliminarPedido(int pedidoId) throws SQLException {
        String sql = "DELETE FROM PEDIDO WHERE pedido_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedidoId);
            return stmt.executeUpdate() > 0;
        }
    }

    // BUSCAR PEDIDO POR ID
    public List<Object> buscarPedido(int pedidoId) throws SQLException {
        String sql = "SELECT * FROM PEDIDO WHERE pedido_id = ?";
        List<Object> pedidoDatos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedidoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pedidoDatos.add(rs.getInt("cliente_id"));
                pedidoDatos.add(rs.getDate("fecha_pedido"));
                pedidoDatos.add(rs.getString("estado"));
                pedidoDatos.add(rs.getBigDecimal("total"));
            }
        }
        return pedidoDatos;
    }
}
