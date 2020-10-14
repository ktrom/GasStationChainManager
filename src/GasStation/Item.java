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
    public Item(int ItemID) throws SQLException {
        // Instantiate gas station id
        this.ItemID = ItemID;

        // Pull all data to this object
        this.pull();
    }

    /**
     * Create a new Item.
     */
    public Item(String Name, Double Price, String PhotoURL, String Notes) throws SQLException {
        // Initialize instance variables
        this.Name = Name;
        this.Price = Price;
        this.PhotoURL = PhotoURL;
        this.Notes = Notes;

        // Insert new row
        this.create();
    }

    /**
     * Get item ID.
     *
     * @return item id
     */
    public int getItemID() {
        return this.ItemID;
    }

    public String getName() throws SQLException {
        this.pull();
        return this.Name;
    }

    public Double getPrice() throws SQLException {
        this.pull();
        return this.Price;
    }

    public String getPhotoURL() throws SQLException {
        this.pull();
        return this.PhotoURL;
    }

    public String getNotes() throws SQLException {
        this.pull();
        return this.Notes;
    }

    public boolean setName(String Name) throws SQLException {
        this.Name = Name;
        return this.push();
    }

    public boolean setPrice(Double Price) throws SQLException {
        this.Price = Price;
        return this.push();
    }

    public boolean setPhotoURL(String PhotoURL) throws SQLException {
        this.PhotoURL = PhotoURL;
        return this.push();
    }

    public boolean setNotes(String Notes) throws SQLException {
        this.Notes = Notes;
        return this.push();
    }

    /**
     * Pull changes to this Item.
     */
    private void pull() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Item WHERE ItemID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.ItemID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        rs.next();

        // Set attributes for this GasStation
        this.ItemID = rs.getInt("ItemID");
        this.Name = rs.getString("Name");
        this.Price = rs.getDouble("Price");
        this.PhotoURL = rs.getString("PhotoURL");
        this.Notes = rs.getString("Notes");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();
    }

    /**
     * Push Item changed fields to the database.
     *
     * @return true if push successful, false otherwise
     */
    private boolean push() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "UPDATE hsnkwamy_GasStation.Item SET Name = ?, Price = ?, PhotoURL = ?, Notes = ? WHERE ItemID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setString(1, this.Name);
        ps.setDouble(2, this.Price);
        ps.setString(3, this.PhotoURL);
        ps.setString(4, this.Notes);
        ps.setInt(5, this.ItemID);

        // Execute the update
        int rowsAffected = ps.executeUpdate();

        // Close all opened streams
        ps.close();
        conn.close();

        return rowsAffected == 1;
    }

    /**
     * Create a new Item in the database.
     */
    private void create() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "INSERT INTO hsnkwamy_GasStation.Item SET Name = ?, Price = ?, PhotoURL = ?, Notes = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.Name);
        ps.setDouble(2, this.Price);
        ps.setString(3, this.PhotoURL);
        ps.setString(4, this.Notes);

        // Execute insert
        ps.executeUpdate();

        // Set the ItemID
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        this.ItemID = rs.getInt(1);

        // Close opened streams
        rs.close();
        ps.close();
        conn.close();
    }
}
