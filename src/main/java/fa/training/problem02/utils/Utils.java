package fa.training.problem02.utils;

import java.sql.SQLException;

/**
 * @author Edward Dao
 * @version 1.0
 */
public final class Utils {
    private Utils() {
    }

    public static final String jdbcURL = "jdbc:mysql://localhost:3306/problem02?useSSL=false&allowPublicKeyRetrieval=true";
    public static final String jdbcUsername = "root";
    public static final String jdbcPassword = "example";

    /**
     * Handles printing {@link SQLException} to console
     * 
     * @param ex SQLException
     */
    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    /**
     * Check whether an object is null or not
     * 
     * @param o Object to validate
     * @return true if o is null, false otherwise
     */
    public static boolean isNull(Object o) {
        return o == null;
    }

}
