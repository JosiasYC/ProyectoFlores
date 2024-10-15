package DAO;

import ConexionDB.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private final Connection conn;

    // Constructor que obtiene la conexión de la instancia única de Conexion
    public ClienteDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // INSERTAR CLIENTE
    public boolean insertarCliente(String nombre, String email, String telefono, String direccion) throws SQLException {
        String sql = "INSERT INTO CLIENTE (nombre, email, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.setString(3, telefono);
            stmt.setString(4, direccion);
            return stmt.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR CLIENTE
    public boolean actualizarCliente(String nombre, String email, String telefono, String direccion) throws SQLException {
        String sql = "UPDATE CLIENTE SET email = ?, telefono = ?, direccion = ? WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, telefono);
            stmt.setString(3, direccion);
            stmt.setString(4, nombre);
            return stmt.executeUpdate() > 0;
        }
    }

    // ELIMINAR CLIENTE
    public boolean eliminarCliente(String nombre) throws SQLException {
        String sql = "DELETE FROM CLIENTE WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            return stmt.executeUpdate() > 0;
        }
    }

    // BUSCAR CLIENTE POR NOMBRE
    public List<String> buscarCliente(String nombre) throws SQLException {
        String sql = "SELECT email, telefono, direccion FROM CLIENTE WHERE nombre = ?";
        List<String> datosCliente = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                datosCliente.add(rs.getString("email"));
                datosCliente.add(rs.getString("telefono"));
                datosCliente.add(rs.getString("direccion"));
            }
        }
        return datosCliente;
    }
}
