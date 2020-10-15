package GasStation;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GasStation {

    /**
     * Gas station id.
     */
    private int GasStationID;

    /**
     * Gas station address.
     */
    private String Location;

    /**
     * Create an existing GasStation.
     *
     * @param GasStationID given GasStationID
     */
    public GasStation(int GasStationID) {
        // Instantiate gas station id
        this.GasStationID = GasStationID;
    }

    /**
     * Create a new GasStation.
     */
    public GasStation(String Location) {
        // Initialize instance variables
        this.Location = Location;
    }

    public int getGasStationID() {
        return this.GasStationID;
    }

    public String getLocation() {
        return this.Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    /**
     * Pull changes to this GasStation.
     *
     * @return true if successful, false otherwise
     */
    public boolean pull() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.GasStation WHERE GasStationID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return false;
        }

        // Set attributes for this GasStation
        this.GasStationID = rs.getInt("GasStationID");
        this.Location = rs.getString("Location");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }

    /**
     * Push GasStation changed fields to the database.
     *
     * @return true if push successful, false otherwise
     */
    public boolean push() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "UPDATE hsnkwamy_GasStation.GasStation SET Location = ? WHERE GasStationID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setString(1, this.Location);
        ps.setInt(2, this.GasStationID);

        // Execute the update
        int rowsAffected = ps.executeUpdate();

        // Close all opened streams
        ps.close();
        conn.close();

        return rowsAffected == 1;
    }

    /**
     * Create a new GasStation in the database.
     *
     * @return true if successful, false otherwise
     */
    public boolean create() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "INSERT INTO hsnkwamy_GasStation.GasStation SET Location = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.Location);

        // Execute insert
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            // Insert failed, duplicate row
            return false;
        }

        // Set the GasStationID
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        this.GasStationID = rs.getInt(1);

        // Close opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }

    public ArrayList<Employee> getEmployees() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT EmployeeID FROM hsnkwamy_GasStation.Employee WHERE GasStationID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);

        // Execute query
        ResultSet rs = ps.executeQuery();

        // Set attributes for this GasStation
        ArrayList<Employee> employees = new ArrayList<Employee>();
        while (rs.next()) {
            int empID;
            empID = (rs.getInt("EmployeeID"));
            Employee e = new Employee(empID);
            e.pull();
            employees.add(e);
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return employees;
    }

}
