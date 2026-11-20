package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String DRIVER;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties props = new Properties();
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load db.properties: " + e.getMessage());
        }
        DRIVER = props.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        URL = props.getProperty("db.url", "jdbc:mysql://localhost:3306/network_asset_manager");
        USER = props.getProperty("db.user", "root");
        PASSWORD = props.getProperty("db.password", "");
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC driver not found: " + DRIVER, e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
