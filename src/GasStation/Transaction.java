package GasStation;

import java.sql.*;

public class Transaction {
    /**
     * Transaction id.
     */
    private int TransactionID;

    /**
     * Item id
     */
    private int ItemID;

    /**
     * Id of the gas station where transaction took place
     */
    private int GasStationID;

    /**
     * Item purchased quantity.
     */
    private Double Quantity;

    /**
     * Date of Transaction.
     */
    private Date DateSold;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getGasStationId() {
        return GasStationID;
    }

    public void setGasStationId(int gasStationId) {
        GasStationID = gasStationId;
    }

    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double quantity) {
        Quantity = quantity;
    }

    public Date getDateSold() {
        return DateSold;
    }

    public void setDateSold(Date dateSold) {
        DateSold = dateSold;
    }

    public Transaction(int TransactionID) {
        // Instantiate Transaction ID
        this.TransactionID = TransactionID;
    }

    public Transaction(int itemID, int gasStationID, Double quantity, Date dateSold) {
        ItemID = itemID;
        GasStationID = gasStationID;
        Quantity = quantity;
        DateSold = dateSold;
    }

    public boolean pull() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Transaction WHERE TransactionID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.TransactionID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return false;
        }

        // Set attributes for this GasStation
        this.ItemID = rs.getInt("ItemID");
        this.GasStationID = rs.getInt("GasStationId");
        this.Quantity = rs.getDouble("Quantity");
        this.DateSold = rs.getDate("DateSold");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }

    public boolean push() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "UPDATE hsnkwamy_GasStation.Transaction SET ItemId = ?, GasStationId = ?, Quantity = ?, DateSold = ? WHERE TransactionID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.ItemID);
        ps.setInt(2, this.GasStationID);
        ps.setDouble(3, this.Quantity);
        ps.setDate(4, this.DateSold);
        ps.setInt(5, this.TransactionID);

        // Execute the update
        int rowsAffected = ps.executeUpdate();

        // Close all opened streams
        ps.close();
        conn.close();

        return rowsAffected == 1;
    }

    public boolean create() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "INSERT INTO hsnkwamy_GasStation.Transaction SET ItemId = ?, GasStationId = ?, Quantity = ?, DateSold = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, this.ItemID);
        ps.setInt(2, this.GasStationID);
        ps.setDouble(3, this.Quantity);
        ps.setDate(4, this.DateSold);

        // Execute insert
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            // Insert failed, duplicate row
            return false;
        }

        // Set the ItemID
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        this.TransactionID = rs.getInt(1);

        // Close opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }
}
