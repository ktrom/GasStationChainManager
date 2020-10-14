package DatabaseClasses;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    private static Connection conn;

    public DatabaseSetup(Connection conn) {
        this.conn = conn;
    }

    public void initializeDatabase() throws SQLException {
        createGasStationTable();
        createInventoryTable();
        createItemTable();
        createItemDescriptionTable();
        createEmployeeTable();
        createScheduleTable();
    }

    private void createGasStationTable() throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS GasStation " +
                "(GasStationID INT PRIMARY KEY AUTO_INCREMENT, " +
                " Location VARCHAR(20)) ";
        stmt.executeUpdate(sql);

    }

    private void createInventoryTable() throws SQLException{
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS Inventory " +
                "(InventoryID INT PRIMARY KEY AUTO_INCREMENT, " +
                "GasStationID INT, " +
                "FOREIGN KEY (GasStationId) REFERENCES GasStation(GasStationID))";

        stmt.executeUpdate(sql);
    }

    private void createItemTable() throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS Item " +
                "(ItemID INT PRIMARY KEY AUTO_INCREMENT, " +
                "InventoryID INT, " +
                "FOREIGN KEY (InventoryId) REFERENCES Inventory(InventoryID))";

        stmt.executeUpdate(sql);
    }

    private void createItemDescriptionTable() throws SQLException{
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS ItemDescription " +
                "(ItemDescriptionID INT PRIMARY KEY AUTO_INCREMENT, " +
                "ItemID INT, " +
                "FOREIGN KEY (ItemId) REFERENCES Item(ItemID))";

        stmt.executeUpdate(sql);
    }

    private void createEmployeeTable() throws SQLException{
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS Employee " +
                "(EmployeeID INT PRIMARY KEY AUTO_INCREMENT, " +
                "EmployeeName VARCHAR(20), " +
                "Salary DOUBLE, " +
                "Location VARCHAR(20), " +
                "Department VARCHAR (20), " +
                "EmployeePosition VARCHAR(20), " +
                "PhotoURL VARCHAR(20))";

        stmt.executeUpdate(sql);
    }

    private void createScheduleTable() throws SQLException{
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS Schedule " +
                "(GasStationID INT, " +
                "FOREIGN KEY (GasStationID) REFERENCES GasStation(GasStationID), " +
                "EmployeeID INT, " +
                "FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID), " +
                "CalenderDate INT, " +
                "Shift INT)";

        stmt.executeUpdate(sql);
    }
}
