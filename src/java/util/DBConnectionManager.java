package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Properties;

public class DBConnectionManager {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            Properties props = new Properties();

            ClassLoader loader = DBConnectionManager.class.getClassLoader();
            InputStream input = loader.getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("db.properties NOT FOUND in classpath!");
            }

            props.load(input);

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

            if (URL == null || USER == null || PASSWORD == null) {
                throw new RuntimeException("db.properties missing required keys!");
            }

        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to load database credentials: " + e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
