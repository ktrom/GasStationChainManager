package DatabaseClasses;

import GasStation.Employee;
import GasStation.Inventory;
import GasStation.Item;
import GasStation.Utilities;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseSupport {

    /**
     * Get an item at a given gas station.
     *
     * @param GasStationID given gas station
     * @param ItemID given item id
     * @return the Item if found, null otherwise
     */
    public static Item getInventoryItem(int GasStationID, int ItemID) throws SQLException {
        // Get the inventory entry for item at the given gas station
        Inventory inv = new Inventory(GasStationID, ItemID);

        // Item is sold by the given gas station
        if (inv.pull()) {
            // Get the gas station item
            Item item = new Item(inv.getItemID());
            item.pull();

            // Return found item
            return item;
        }

        // Item not found
        return null;
    }

    /**
     * Add an item to be sold at a gas station.
     *
     * @param GasStationID given gas station
     * @param ItemID item to begin sale of
     * @return Item created
     */
    public static void addInventoryItem(int GasStationID, int ItemID) throws SQLException {
        // Create new inventory entry
        Inventory inv = new Inventory(GasStationID, ItemID, 0);
        inv.create();
    }

    /**
     * Calculate the total sale price.
     *
     * @param ItemID item to sell
     * @param Quantity quantity of item to sell
     * @return total sale price
     */
    public static Double calculateSaleTotal(int ItemID, int Quantity) throws SQLException {
        // Get the given item
        Item item = new Item(ItemID);
        item.pull();

        // Return total
        return item.getPrice() * Quantity;
    }

    /**
     * Determine if an item exists.
     *
     * @param ItemID given item
     * @return true if item is valid, false otherwise
     */
    public static boolean isValidItem(int ItemID) throws SQLException {
        Item item = new Item(ItemID);
        return item.pull();
    }

    /**
     * Determine if a gas station sells an item.
     *
     * @param GasStationID gas station to check
     * @param ItemID item to check
     * @return true if the gas station sells the item, false otherwise
     */
    public static boolean sellsItem(int GasStationID, int ItemID) throws SQLException {
        Inventory inv = new Inventory(GasStationID, ItemID);
        return inv.pull();
    }

    /**
     * Remove quantity from inventory at a gas station.
     *
     * @param GasStationID gas station to remove inventory from
     * @param ItemID item to remove
     * @param Quantity quantity of item to remove
     */
    public static void removeInventory(int GasStationID, int ItemID, int Quantity) throws SQLException {
        // Get current  inventory
        Inventory inv = new Inventory(GasStationID, ItemID);
        inv.pull();

        // Remove inventory
        inv.setQuantity(inv.getQuantity() - Quantity);
        inv.push();
    }

    /**
     * Gets all employees at this gas station
     * @return An ArrayList of the Employees that work at this gas station
     * @throws SQLException if unsuccessful query
     */
    public static ArrayList<Employee> getStationEmployees(int GasStationID) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT EmployeeID FROM hsnkwamy_GasStation.Employee WHERE GasStationID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, GasStationID);

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

    /**
     * Returns a string representation of the schedule for this gas station
     * @return Ready-To-Print schedule as String
     * @throws SQLException if unsuccessful query
     */
    public static String gasStationScheduleString(int GasStationID) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Schedule, hsnkwamy_GasStation.Employee WHERE Employee.GasStationID = ? AND Employee.EmployeeID = Schedule.EmployeeID " +
                "ORDER BY DATE ASC, SHIFT ";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, GasStationID);

        // Execute query
        ResultSet rs = ps.executeQuery();

        String schedule = "";
        // Set attributes for this GasStation

        while (rs.next()) {
            String name = (rs.getString("Name"));
            Date Date = (rs.getDate("Date"));
            int shift = (rs.getInt("Shift"));
            schedule+= name + " is scheduled for " + Date.toString().substring(0,10) + " for shift " + shift +"\n";
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return schedule;
    }
}
