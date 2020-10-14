package GasStation;

import java.sql.*;

public class Inventory {

    /**
     * Gas station we're tracking inventory for.
     */
    private int GasStationID;

    /**
     * Item we're tracking inventory for.
     */
    private int ItemID;

    /**
     * Quantity of the item available (min of 0).
     */
    private int Quantity;

    /**
     * Construct inventory levels for an existing inventory entry.
     *
     * @param GasStationID gas station id
     * @param ItemID item id
     */
    public Inventory(int GasStationID, int ItemID) throws SQLException {
        // Initialize instance variables
        this.GasStationID = GasStationID;
        this.ItemID = ItemID;

        // Pull all data to this object
        this.pull();
    }

    /**
     * Construct a new inventory entry for an existing gas station and item at the given starting quantity (min 0).
     *
     * @param GasStationID gas station to track inventory at
     * @param ItemID item to track inventory of
     * @param Quantity starting quantity of item
     */
    public Inventory(int GasStationID, int ItemID, int Quantity) throws SQLException {
        // Initialize instance variables
        this.GasStationID = GasStationID;
        this.ItemID = ItemID;
        this.Quantity = Quantity;

        // Push inventory to database
        this.create();
    }

    public int getGasStationID() {
        return this.GasStationID;
    }

    public int getItemID() {
        return this.ItemID;
    }

    public int getQuantity() {
        return this.Quantity;
    }

    public boolean setQuantity(int Quantity) throws SQLException {
        this.Quantity = Quantity;
        return this.push();
    }

    /**
     * Pull changes to this Inventory.
     */
    private void pull() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Inventory WHERE GasStationID = ? AND ItemID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);
        ps.setInt(2, this.ItemID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        rs.next();

        // Set attributes for this GasStation
        this.GasStationID = rs.getInt("GasStationID");
        this.ItemID = rs.getInt("ItemID");
        this.Quantity = rs.getInt("Quantity");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();
    }

    /**
     * Push Inventory changed fields to the database.
     *
     * @return true if push successful, false otherwise
     */
    private boolean push() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "UPDATE hsnkwamy_GasStation.Inventory SET Quantity = ? WHERE GasStationID = ? AND ItemID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.Quantity);
        ps.setInt(2, this.GasStationID);
        ps.setInt(3, this.ItemID);

        // Execute the update
        int rowsAffected = ps.executeUpdate();

        // Close all opened streams
        ps.close();
        conn.close();

        return rowsAffected == 1;
    }

    /**
     * Create a new Inventory entry in the database.
     */
    private void create() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "INSERT INTO hsnkwamy_GasStation.Inventory SET GasStationID = ?, ItemID = ?, Quantity = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);
        ps.setInt(2, this.ItemID);
        ps.setInt(3, this.Quantity);

        // Execute insert
        ps.executeUpdate();

        // Close opened streams
        ps.close();
        conn.close();
    }
}
