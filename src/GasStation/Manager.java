package GasStation;

import DatabaseClasses.DatabaseSetup;

import java.sql.Connection;
import java.sql.SQLException;

public class Manager {

    public static void main(String[] args) throws SQLException {

        // Initial database setup
        Connection conn = Utilities.getConnection();
        DatabaseSetup.initializeDatabase(conn);


    }
}
