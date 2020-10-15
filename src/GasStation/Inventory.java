package GasStation;

import Interfaces.Model;

import java.sql.*;

public class Inventory implements Model {

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
    public Inventory(int GasStationID, int ItemID) {
        // Initialize instance variables
        this.GasStationID = GasStationID;
        this.ItemID = ItemID;
    }

    /**
     * Construct a new inventory entry for an existing gas station and item at the given starting quantity (min 0).
     *
     * @param GasStationID gas station to track inventory at
     * @param ItemID item to track inventory of
     * @param Quantity starting quantity of item
     */
    public Inventory(int GasStationID, int ItemID, int Quantity) {
        // Initialize instance variables
        this.GasStationID = GasStationID;
        this.ItemID = ItemID;
        this.Quantity = Quantity;
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

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    /**
     * Pull changes to this Inventory.
     *
     * @return true if successful, false otherwise
     */
    public boolean pull(){
        try {
            return pullHelper();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    private boolean pullHelper() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Inventory WHERE GasStationID = ? AND ItemID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);
        ps.setInt(2, this.ItemID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return false;
        }

        // Set attributes for this GasStation
        this.GasStationID = rs.getInt("GasStationID");
        this.ItemID = rs.getInt("ItemID");
        this.Quantity = rs.getInt("Quantity");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }

    /**
     * Push Inventory changed fields to the database.
     *
     * @return true if push successful, false otherwise
     */
    public boolean push(){
        try {
            return pushHelper();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    private boolean pushHelper() throws SQLException {
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
     *
     * @return true if successful, false otherwise
     */
    public boolean create(){
        try {
            return createHelper();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    private boolean createHelper() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "INSERT INTO hsnkwamy_GasStation.Inventory SET GasStationID = ?, ItemID = ?, Quantity = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);
        ps.setInt(2, this.ItemID);
        ps.setInt(3, this.Quantity);

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
