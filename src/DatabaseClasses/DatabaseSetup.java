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
    }

    private static void createGasStationTable() throws SQLException {
        Statement stmt = conn.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS GasStation " +
                "(station_id INT PRIMARY KEY AUTO_INCREMENT, " +
                " Location VARCHAR(20)) ";
        stmt.executeUpdate(sql);
    }
    private void createScheduleTable(){

    }

    private void createDatesTable(){

    }
}
