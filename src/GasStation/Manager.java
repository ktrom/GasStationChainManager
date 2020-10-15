package GasStation;

import Controllers.FinancialController;
import Controllers.ScheduleController;
import Controllers.TaskController;
import Controllers.TransactionController;
import DatabaseClasses.DatabaseSetup;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Manager {

    public static void main(String[] args) throws SQLException {

        // Initial database setup
        Connection conn = Utilities.getConnection();
        DatabaseSetup.initializeDatabase(conn);

    }
}
