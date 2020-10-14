package DatabaseClasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    /**
     * Database connection instance.
     */
    private static Connection conn;

    public DatabaseSetup(Connection conn) {
        this.conn = conn;
    }

    /**
     * Setup all initial database tables if not already in place.
     *
     * @throws SQLException
     */
    public void initializeDatabase() throws SQLException {
        createGasStationTable();
        createEmployeeTable();
        createItemTable();
        createInventoryTable();
        createScheduleTable();
    }

    private void createGasStationTable() throws SQLException {
        Statement stmt = conn.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS GasStation(" +
                    "GasStationID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT," +
                    "Location VARCHAR(50)\n" +
                ")";
        stmt.executeUpdate(sql);
    }

    private void createEmployeeTable() throws SQLException {
        Statement stmt = conn.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS Employee(" +
                    "EmployeeID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT," +
                    "GasStationID INT UNSIGNED NOT NULL," +
                    "Name VARCHAR(100) NOT NULL," +
                    "SSN VARCHAR(11) NOT NULL," +
                    "Salary DOUBLE NOT NULL," +
                    "Department VARCHAR(20)," +
                    "EmployeePosition ENUM('coo', 'cfo', 'manager', 'hiring_manager', 'attendant') NOT NULL," +
                    "StartDate TIMESTAMP NOT NULL DEFAULT NOW()," +
                    "FOREIGN KEY(GasStationID) REFERENCES GasStation(GasStationID)" +
                ")";
        stmt.executeUpdate(sql);
    }

    private void createItemTable() throws SQLException {
        Statement stmt = conn.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS Item(" +
                    "ItemID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT," +
                    "Name VARCHAR(20) NOT NULL," +
                    "Price DOUBLE NOT NULL," +
                    "PhotoURL TEXT," +
                    "Notes TEXT" +
                ")";
        stmt.executeUpdate(sql);
    }

    private void createInventoryTable() throws SQLException {
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

    private void createScheduleTable() throws SQLException {
        Statement stmt = conn.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS Schedule(" +
                    "GasStationID INT UNSIGNED," +
                    "EmployeeID INT UNSIGNED," +
                    "Date DATE NOT NULL," +
                    "Shift ENUM('1', '2', '3') NOT NULL" +
                ")";
        stmt.executeUpdate(sql);
    }
}
