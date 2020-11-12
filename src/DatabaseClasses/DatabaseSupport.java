package DatabaseClasses;

import Controllers.EmployeeController;
import Controllers.TaskController;
import GasStation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

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

    /**
     * Returns a string of all assigned tasks at given gas station
     * @param GasStationID ID of gas station
     * @return String representation of all tasks at gas station
     * @throws SQLException if query/connection is bad
     */
    public static String gasStationTasksString(int GasStationID) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Task, hsnkwamy_GasStation.Employee WHERE Employee.GasStationID = ? AND Employee.EmployeeID = Task.EmployeeID ";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, GasStationID);

        // Execute query
        ResultSet rs = ps.executeQuery();

        String schedule = "";
        // Set attributes for this GasStation

        while (rs.next()) {
            String name = (rs.getString("Name"));
            String taskDescription = (rs.getString("TaskDescription"));
            schedule+= name + " has task: " + taskDescription + "\n";
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return schedule;
    }

    /**
     * Returns all tasks for a given employee
     * @param employeeID ID of employee
     * @return an arraylist of the Tasks for given employee
     * @throws SQLException if query/connection fails
     */
    public static ArrayList<Task> getEmployeeTasks(int employeeID) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Task WHERE Task.EmployeeID = ? ";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, employeeID);

        // Execute query
        ResultSet rs = ps.executeQuery();

        String schedule = "";
        // Set attributes for this GasStation


        ArrayList<Task> employeeTasks = new ArrayList<Task>();
        while (rs.next()) {
            int taskID = (rs.getInt("TaskID"));
            Task t = new Task(taskID);
            t.pull();
             employeeTasks.add(t);
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return employeeTasks;
    }

    /**
     * Deletes task from database
     * @param taskID ID of task to be removed
     * @return true if successful deletion, false otherwise
     * @throws SQLException if query or connection fails
     */
    public static boolean deleteTask(int taskID) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "DELETE FROM hsnkwamy_GasStation.Task WHERE Task.TaskID = ? ";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, taskID);

        // Execute query
        ps.executeUpdate();

        // Close all opened streams
        ps.close();
        conn.close();

        return true;
    }

    /**
     * Assigns random tasks to employees in the gas station
     * @param GasStationID ID of gas station
     * @param descriptions Descriptions of tasks
     * @return true if successful, false othersie
     * @throws SQLException if failed connection or query
     */
    public static boolean assignRandomTasks(int GasStationID, ArrayList<String> descriptions) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Employee WHERE Employee.GasStationID = ? ";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, GasStationID);

        // Execute query
        ResultSet rs = ps.executeQuery();

        String schedule = "";
        // Set attributes for this GasStation

        ArrayList<Employee> employees = new ArrayList<Employee>();
        while (rs.next()) {
            int employeeID = (rs.getInt("EmployeeID"));
            Employee e = new Employee(employeeID);
            e.pull();
            employees.add(e);
        }

        employees.removeIf(emp->emp.getEmployeePosition() == EmployeePosition.MANAGER);
        Collections.shuffle(employees);
        for(int i = 0; i < descriptions.size(); i++){
            TaskController ts = new TaskController();
            ts.assignTask(GasStationID, employees.get(i % employees.size()).getEmployeeID(), descriptions.get(i));
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();


        return true;
    }

    /**
     * Deletes employee from database
     * @param employeeID ID of employee to be removed
     * @return true if successful deletion, false otherwise
     * @throws SQLException if query or connection fails
     */
    public static boolean deleteEmployee(int employeeID) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "DELETE FROM hsnkwamy_GasStation.Employee WHERE Employee.EmployeeID = ? ";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, employeeID);

        // Execute query
        ps.executeUpdate();

        // Close all opened streams
        ps.close();
        conn.close();

        return true;
    }

    /**
     * Determine if a phone number is valid (not in use by another gas station).
     *
     * @param PhoneNumber given phone number
     * @return true if phone number valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String PhoneNumber) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT COUNT(*) AS num FROM hsnkwamy_GasStation.GasStation WHERE PhoneNumber = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setString(1, PhoneNumber);

        // Execute query
        ResultSet rs = ps.executeQuery();

        // Determine how many stations have the given phone number
        rs.next();
        return rs.getInt("num") <= 0;
    }

    /**
     * Determine if a gas station location is valid (not in use by another gas station).
     *
     * @param Location given location
     * @return true if location valid, false otherwise
     */
    public static boolean isValidStationLocation(String Location) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT COUNT(*) AS num FROM hsnkwamy_GasStation.GasStation WHERE Location = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setString(1, Location);

        // Execute query
        ResultSet rs = ps.executeQuery();

        // Determine how many stations have the given phone number
        rs.next();
        return rs.getInt("num") <= 0;
    }

    /**
     * Determine if an item is already tracked.
     *
     * @param ItemName given item name
     * @return true if item already exists, false otherwise
     */
    public static boolean itemUnique(String ItemName) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT COUNT(*) AS num FROM hsnkwamy_GasStation.Item WHERE Name = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setString(1, ItemName);

        // Execute query
        ResultSet rs = ps.executeQuery();

        // Determine if other items have the same name
        rs.next();
        return rs.getInt("num") <= 0;
    }

    /**
     * Get the latest constructed gas station.
     *
     * @return id of the gas station
     */
    public static int getLatestGasStation() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT MAX(GasStationID) AS station FROM hsnkwamy_GasStation.GasStation";
        PreparedStatement ps = conn.prepareStatement(stationQuery);

        // Execute query
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("station");
    }

    /**
     * Determine if a gas station with the given id already exists.
     *
     * @param stationId given gas station id
     * @return true if the station exists, false otherwise
     */
    public static boolean gasStationExists(int stationId) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT GasStationId FROM hsnkwamy_GasStation.GasStation WHERE GasStationId = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, stationId);

        // Execute query
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
