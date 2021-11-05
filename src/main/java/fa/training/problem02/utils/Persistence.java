package fa.training.problem02.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Edward Dao
 * @version 1.0
 */
public final class Persistence {
    private static Persistence instance;

    private Persistence() {
    }

    public static Persistence getInstance() {
        if (instance == null) {
            instance = new Persistence();
        }
        return instance;
    }

    /**
     * Responsible for creating connection to desired database as information
     * declared in {@link Utils}
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            // Class.forName("com.mysql.cj.jdbc.Driver");
            // Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(Utils.jdbcURL, Utils.jdbcUsername, Utils.jdbcPassword);
            // if (connection != null) {
            // System.out.println("Connected!");
            // }

            // } catch (ClassNotFoundException e) {
            // e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
