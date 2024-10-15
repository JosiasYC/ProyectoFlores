package DAO;

import ConexionDB.Conexion;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorDAO {

    private final Connection conn;

    // Constructor que obtiene la conexión única desde la clase Conexion
    public TrabajadorDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // INSERTAR TRABAJADOR
    public boolean insertarTrabajador(String nombre, String cargo, String horario, BigDecimal salario) throws SQLException {
        String sql = "INSERT INTO TRABAJADOR (nombre, cargo, horario, salario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, cargo);
            stmt.setString(3, horario);
            stmt.setBigDecimal(4, salario);
            return stmt.executeUpdate() > 0;
        }
    }

    // ACTUALIZAR TRABAJADOR
    public boolean actualizarTrabajador(String nombre, String cargo, String horario, BigDecimal salario) throws SQLException {
        String sql = "UPDATE TRABAJADOR SET cargo = ?, horario = ?, salario = ? WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cargo);
            stmt.setString(2, horario);
            stmt.setBigDecimal(3, salario);
            stmt.setString(4, nombre);
            return stmt.executeUpdate() > 0;
        }
    }

    // ELIMINAR TRABAJADOR
    public boolean eliminarTrabajador(String nombre) throws SQLException {
        String sql = "DELETE FROM TRABAJADOR WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            return stmt.executeUpdate() > 0;
        }
    }

    // BUSCAR TRABAJADOR POR NOMBRE
    public List<String> buscarTrabajador(String nombre) throws SQLException {
        String sql = "SELECT cargo, horario, salario, fecha_registro FROM TRABAJADOR WHERE nombre = ?";
        List<String> datosTrabajador = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                datosTrabajador.add(rs.getString("cargo"));
                datosTrabajador.add(rs.getString("horario"));
                datosTrabajador.add(rs.getBigDecimal("salario").toString());
                datosTrabajador.add(rs.getString("fecha_registro"));
            }
        }
        return datosTrabajador;
    }
}
