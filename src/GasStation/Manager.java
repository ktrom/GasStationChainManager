package GasStation;

import java.sql.Connection;
import java.sql.SQLException;

public class Manager {
    public static void main(String[] args) throws SQLException {
        System.out.println("manage");

        // Example database connection
        Connection conn = Utilities.getConnection();
        System.out.println(conn.toString());
    }
}
