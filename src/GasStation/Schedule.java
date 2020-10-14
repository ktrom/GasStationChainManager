package GasStation;

import java.sql.*;

public class Schedule {

    /**
     * Gas station to schedule at.
     */
    private int GasStationID;

    /**
     * Employee being scheduled.
     */
    private int EmployeeID;

    /**
     * Scheduled date.
     */
    private Date Date;

    /**
     * Scheduled shift number (1/2/3).
     */
    private String Shift;

    /**
     * Construct schedule for an existing entry.
     *
     * @param GasStationID gas station id
     * @param EmployeeID employee being scheduled
     */
    public Schedule(int GasStationID, int EmployeeID) throws SQLException {
        // Initialize instance variables
        this.GasStationID = GasStationID;
        this.EmployeeID = EmployeeID;

        // Pull existing schedule to this Schedule
        this.pull();
    }

    public Schedule(int GasStationID, int EmployeeID, Date Date, String Shift) throws SQLException {
        // Instantiate instance variables
        this.GasStationID = GasStationID;
        this.EmployeeID = EmployeeID;
        this.Date = Date;
        this.Shift = Shift;

        // Create the new schedule
        this.create();
    }

    public int getGasStationID() {
        return this.GasStationID;
    }

    public int getEmployeeID() {
        return this.EmployeeID;
    }

    public Date getDate() throws SQLException {
        this.pull();
        return this.Date;
    }

    public String getShift() throws SQLException {
        this.pull();
        return this.Shift;
    }

    public boolean setDate(Date Date) throws SQLException {
        this.Date = Date;
        return this.push();
    }

    public boolean setShift(String Shift) throws SQLException {
        this.Shift = Shift;
        return this.push();
    }

    /**
     * Pull changes to this Schedule.
     */
    private void pull() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Schedule WHERE GasStationID = ? AND EmployeeID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);
        ps.setInt(2, this.EmployeeID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        rs.next();

        // Set attributes for this GasStation
        this.GasStationID = rs.getInt("GasStationID");
        this.EmployeeID = rs.getInt("EmployeeID");
        this.Date = rs.getDate("Date");
        this.Shift = rs.getString("Shift");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();
    }

    /**
     * Push Schedule changed fields to the database.
     *
     * @return true if push successful, false otherwise
     */
    private boolean push() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "UPDATE hsnkwamy_GasStation.Schedule SET Date = ?, Shift = ? WHERE GasStationID = ? AND EmployeeID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setDate(1, this.Date);
        ps.setString(2, this.Shift);
        ps.setInt(3, this.GasStationID);
        ps.setInt(4, this.EmployeeID);

        // Execute the update
        int rowsAffected = ps.executeUpdate();

        // Close all opened streams
        ps.close();
        conn.close();

        return rowsAffected == 1;
    }

    /**
     * Create a new Schedule entry in the database.
     */
    private void create() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "INSERT INTO hsnkwamy_GasStation.Schedule SET GasStationID = ?, EmployeeID = ?, Date = ?, Shift = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);
        ps.setInt(2, this.GasStationID);
        ps.setDate(3, this.Date);
        ps.setString(4, this.Shift);

        // Execute insert
        ps.executeUpdate();

        // Close opened streams
        ps.close();
        conn.close();
    }
}
