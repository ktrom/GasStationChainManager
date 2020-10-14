package GasStation;

import java.sql.*;

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
     * Get an existing GasStation by id.
     *
     * @param GasStationID given GasStationID
     */
    public GasStation(int GasStationID) throws SQLException {
        // Instantiate gas station id
        this.GasStationID = GasStationID;

        // Pull all data to this object
        this.pull();
    }

    /**
     * Create a new GasStation.
     */
    public GasStation(String Location) throws SQLException {
        // Initialize location
        this.Location = Location;

        // Insert new row
        this.create();
    }

    /**
     * Get gas station id.
     *
     * @return gas station id
     */
    public int getGasStationID() {
        return this.GasStationID;
    }

    /**
     * Pull potential changes and return the location.
     *
     * @return current location
     */
    public String getLocation() throws SQLException {
        this.pull();
        return this.Location;
    }

    /**
     * Set new location and push changes.
     *
     * @param Location new location
     * @return true if location updated, false otherwise
     */
    public boolean setLocation(String Location) throws SQLException {
        this.Location = Location;
        return this.push();
    }

    /**
     * Pull changes to this GasStation.
     */
    private void pull() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.GasStation WHERE GasStationID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        rs.next();

        // Set attributes for this GasStation
        this.GasStationID = rs.getInt("GasStationID");
        this.Location = rs.getString("Location");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();
    }

    /**
     * Push GasStation changed fields to the database.
     *
     * @return true if push successful, false otherwise
     */
    private boolean push() throws SQLException {
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
     */
    private void create() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "INSERT INTO hsnkwamy_GasStation.GasStation SET Location = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.Location);

        // Execute insert
        ps.executeUpdate();

        // Set the GasStationID
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        this.GasStationID = rs.getInt(1);

        // Close opened streams
        rs.close();
        ps.close();
        conn.close();
    }
}
