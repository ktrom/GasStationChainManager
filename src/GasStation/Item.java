package GasStation;

import java.sql.*;

public class Item {

    /**
     * Item id.
     */
    private int ItemID;

    /**
     * Item name.
     */
    private String Name;

    /**
     * Item price.
     */
    private Double Price;

    /**
     * Cost for the chain to purchase this item from the supplier.
     */
    private Double SupplierPrice;

    /**
     * Link to an image of this Item.
     */
    private String PhotoURL;

    /**
     * Notes on this Item.
     */
    private String Notes;

    /**
     * Get an existing Item by id.
     *
     * @param ItemID given ItemID
     */
    public Item(int ItemID) {
        // Instantiate gas station id
        this.ItemID = ItemID;
    }

    /**
     * Create a new item.
     *
     * @param Name item name
     * @param Price item price
     * @param SupplierPrice chain purchase price
     * @param PhotoURL link to photo of item
     * @param Notes notes on item
     */
    public Item(String Name, Double Price, Double SupplierPrice, String PhotoURL, String Notes) {
        // Initialize instance variables
        this.Name = Name;
        this.Price = Price;
        this.SupplierPrice = SupplierPrice;
        this.PhotoURL = PhotoURL;
        this.Notes = Notes;
    }

    public int getItemID() {
        return this.ItemID;
    }

    public String getName() {
        return this.Name;
    }

    public Double getPrice() {
        return this.Price;
    }

    public Double getSupplierPrice() {
        return this.SupplierPrice;
    }

    public String getPhotoURL() {
        return this.PhotoURL;
    }

    public String getNotes() {
        return this.Notes;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setPrice(Double Price) {
        this.Price = Price;
    }

    public void setSupplierCost(Double SupplierCost) {
        this.SupplierPrice = SupplierPrice;
    }

    public void setPhotoURL(String PhotoURL) {
        this.PhotoURL = PhotoURL;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }

    /**
     * Pull changes to this Item.
     *
     * @return true if successful, false otherwise
     */
    public boolean pull() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Item WHERE ItemID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.ItemID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return false;
        }

        // Set attributes for this GasStation
        this.ItemID = rs.getInt("ItemID");
        this.Name = rs.getString("Name");
        this.Price = rs.getDouble("Price");
        this.SupplierPrice = rs.getDouble("SupplierPrice");
        this.PhotoURL = rs.getString("PhotoURL");
        this.Notes = rs.getString("Notes");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }

    /**
     * Push Item changed fields to the database.
     *
     * @return true if push successful, false otherwise
     */
    public boolean push() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "UPDATE hsnkwamy_GasStation.Item SET Name = ?, Price = ?, SupplierPrice = ?, PhotoURL = ?, Notes = ? WHERE ItemID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setString(1, this.Name);
        ps.setDouble(2, this.Price);
        ps.setDouble(3, this.SupplierPrice);
        ps.setString(4, this.PhotoURL);
        ps.setString(5, this.Notes);
        ps.setInt(6, this.ItemID);

        // Execute the update
        int rowsAffected = ps.executeUpdate();

        // Close all opened streams
        ps.close();
        conn.close();

        return rowsAffected == 1;
    }

    /**
     * Create a new Item in the database.
     *
     * @return true if successful, false otherwise
     */
    public boolean create() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "INSERT INTO hsnkwamy_GasStation.Item SET Name = ?, Price = ?, SupplierPrice = ?, PhotoURL = ?, Notes = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.Name);
        ps.setDouble(2, this.Price);
        ps.setDouble(3, this.SupplierPrice);
        ps.setString(4, this.PhotoURL);
        ps.setString(5, this.Notes);

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
        this.ItemID = rs.getInt(1);

        // Close opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }
}
