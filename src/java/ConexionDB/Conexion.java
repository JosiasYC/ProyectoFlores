package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/flores";
    private static final String USERNAME = "root"; // Cambia por tu usuario de MySQL
    private static final String PASSWORD = "jjyc"; // Cambia por tu contraseña de MySQL

    // Instancia estática única de la conexión
    private static Conexion instance;

    // Constructor privado para evitar instanciación externa
    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener la instancia única de la conexión
    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    // Método para obtener una nueva conexión
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
