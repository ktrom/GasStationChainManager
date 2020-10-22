package DatabaseClasses;

import GasStation.Employee;
import GasStation.Utilities;

import java.sql.*;
import java.util.ArrayList;

/**
 * Creates all necessary tables in the database if nonexistent
 */
public class DatabaseSetup {

    /**
     * Setup all initial database tables if not already in place.
     *
     * @throws SQLException
     */
    public static void initializeDatabase(Connection conn) throws SQLException {
        createGasStationTable(conn);
        createEmployeeTable(conn);
        createItemTable(conn);
        createInventoryTable(conn);
        createScheduleTable(conn);
        createTransactionTable(conn);
        createTaskTable(conn);
    }

    /**
     * Creates a table for the GasStation class
     * @param conn The Connection to the database (JDBC)
     * @throws SQLException if unsuccessful execution of query
     */
    private static void createGasStationTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS GasStation(" +
                    "GasStationID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT," +
                    "Location VARCHAR(50) UNIQUE" +
                ")";
        stmt.executeUpdate(sql);
    }

    /**
     * Creates a table for the Employee class
     * @param conn The Connection to the database (JDBC)
     * @throws SQLException if unsuccessful execution of query
     */
    private static void createEmployeeTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS Employee(" +
                    "EmployeeID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT," +
                    "GasStationID INT UNSIGNED NOT NULL," +
                    "Name VARCHAR(100) NOT NULL," +
                    "SSN VARCHAR(11) NOT NULL," +
                    "Salary DOUBLE NOT NULL," +
                    "Department VARCHAR(20)," +
                    "EmployeePosition ENUM('COO', 'CFO', 'MANAGER', 'HIRING_MANAGER', 'ATTENDANT') NOT NULL," +
                    "StartDate TIMESTAMP NOT NULL DEFAULT NOW()," +
                    "FOREIGN KEY(GasStationID) REFERENCES GasStation(GasStationID)" +
                ")";
        stmt.executeUpdate(sql);
    }

    /**
     * Creates a table for the Item class
     * @param conn The Connection to the database (JDBC)
     * @throws SQLException if unsuccessful execution of query
     */
    private static void createItemTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS Item(" +
                    "ItemID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT," +
                    "Name VARCHAR(20) NOT NULL," +
                    "Price DOUBLE NOT NULL," +
                    "SupplierPrice DOUBLE NOT NULL," +
                    "PhotoURL TEXT," +
                    "Notes TEXT" +
                ")";
        stmt.executeUpdate(sql);
    }

    /**
     * Creates a table for the Inventory class
     * @param conn The Connection to the database (JDBC)
     * @throws SQLException if unsuccessful execution of query
     */
    private static void createInventoryTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS Inventory(" +
                    "GasStationID INT UNSIGNED," +
                    "ItemID INT UNSIGNED," +
                    "Quantity INT UNSIGNED NOT NULL DEFAULT 0," +
                    "FOREIGN KEY(ItemID) REFERENCES Item(ItemID)," +
                    "FOREIGN KEY(GasStationID) REFERENCES GasStation(GasStationID)," +
                    "PRIMARY KEY(GasStationID, ItemID)" +
                ")";
        stmt.executeUpdate(sql);
    }

    /**
     * Creates a table for the Schedule class
     * @param conn The Connection to the database (JDBC)
     * @throws SQLException if unsuccessful execution of query
     */
    private static void createScheduleTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS Schedule(" +
                    "GasStationID INT UNSIGNED," +
                    "EmployeeID INT UNSIGNED," +
                    "Date DATE NOT NULL," +
                    "Shift ENUM('1', '2', '3') NOT NULL," +
                    "PRIMARY KEY(GasStationID, EmployeeID, Date, Shift)" +
                ")";
        stmt.executeUpdate(sql);
    }

    /**
     * Creates a table for the Transaction class in the database 
     * @param conn The Connection to the database (JDBC)
     * @throws SQLException if unsuccessful execution of query
     */
    private static void createTransactionTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS Transaction(" +
                        "TransactionID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT," +
                        "GasStationID INT UNSIGNED," +
                        "ItemID INT UNSIGNED," +
                        "Quantity INT UNSIGNED NOT NULL DEFAULT 0," +
                        "DateSold DATE ," +
                        "FOREIGN KEY(ItemID) REFERENCES Item(ItemID)," +
                        "FOREIGN KEY(GasStationID) REFERENCES GasStation(GasStationID)" +
                        ")";
        stmt.executeUpdate(sql);
    }


    /**
     * Creates a table for the Task class in the database
     * @param conn The Connection to the database (JDBC)
     * @throws SQLException if unsuccessful execution of query
     */
    private static void createTaskTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS Task(" +
                        "TaskID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT," +
                        "GasStationID INT UNSIGNED," +
                        "EmployeeID INT UNSIGNED," +
                        "TaskDescription VARCHAR (50)," +
                        "FOREIGN KEY(GasStationID) REFERENCES GasStation(GasStationID)," +
                        "FOREIGN KEY(EmployeeID) REFERENCES Employee(EmployeeID)" +
                        ")";
        stmt.executeUpdate(sql);
    }


}
