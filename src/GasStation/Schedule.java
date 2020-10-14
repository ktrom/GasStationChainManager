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
    public Schedule(int GasStationID, int EmployeeID) {
        // Initialize instance variables
        this.GasStationID = GasStationID;
        this.EmployeeID = EmployeeID;
    }

    /**
     * Create a new schedule.
     *
     * @param GasStationID gas station to schedule for
     * @param EmployeeID employee to schedule
     * @param Date date to schedule for
     * @param Shift shift number to schedule
     */
    public Schedule(int GasStationID, int EmployeeID, Date Date, String Shift) {
        // Instantiate instance variables
        this.GasStationID = GasStationID;
        this.EmployeeID = EmployeeID;
        this.Date = Date;
        this.Shift = Shift;
    }

    public int getGasStationID() {
        return this.GasStationID;
    }

    public int getEmployeeID() {
        return this.EmployeeID;
    }

    public Date getDate() {
        return this.Date;
    }

    public String getShift() {
        return this.Shift;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public void setShift(String Shift) {
        this.Shift = Shift;
    }

    /**
     * Pull changes to this Schedule.
     *
     * @return true if successful, false otherwise
     */
    public boolean pull() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Schedule WHERE GasStationID = ? AND EmployeeID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);
        ps.setInt(2, this.EmployeeID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return false;
        }

        // Set attributes for this GasStation
        this.GasStationID = rs.getInt("GasStationID");
        this.EmployeeID = rs.getInt("EmployeeID");
        this.Date = rs.getDate("Date");
        this.Shift = rs.getString("Shift");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }

    /**
     * Push Schedule changed fields to the database.
     *
     * @return true if push successful, false otherwise
     */
    public boolean push() throws SQLException {
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
     *
     * @return true if successful, false otherwise
     */
    public boolean create() throws SQLException {
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
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            // Insert failed, duplicate row
            return false;
        }

        // Close opened streams
        ps.close();
        conn.close();

        return true;
    }
}
