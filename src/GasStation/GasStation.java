package GasStation;

import HelperClasses.HelperFunctions;
import Interfaces.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GasStation implements Model {

    /**
     * Gas station id.
     */
    private int GasStationID;

    /**
     * Gas station address.
     */
    private String Location;

    /**
     * Create an existing GasStation.
     *
     * @param GasStationID given GasStationID
     */
    public GasStation(int GasStationID) {
        // Instantiate gas station id
        this.GasStationID = GasStationID;
    }

    /**
     * Create a new GasStation.
     */
    public GasStation(String Location) {
        // Initialize instance variables
        this.Location = Location;
    }

    public int getGasStationID() {
        return this.GasStationID;
    }

    public String getLocation() {
        return this.Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    /**
     * Pull changes to this GasStation.
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
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.GasStation WHERE GasStationID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return false;
        }

        // Set attributes for this GasStation
        this.GasStationID = rs.getInt("GasStationID");
        this.Location = rs.getString("Location");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }

    /**
     * Push GasStation changed fields to the database.
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
        String stationQuery = "INSERT INTO hsnkwamy_GasStation.GasStation SET Location = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.Location);

        // Execute insert
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            // Insert failed, duplicate row
            return false;
        }

        // Set the GasStationID
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        this.GasStationID = rs.getInt(1);

        // Close opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }


    /**
     * Gets all employees at this gas station
     * @return An ArrayList of the Employees that work at this gas station
     * @throws SQLException if unsuccessful query
     */
    public ArrayList<Employee> getEmployees() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT EmployeeID FROM hsnkwamy_GasStation.Employee WHERE GasStationID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);

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
     * Calculates the total revenue at this gas station for the given time period - this includes employee salary deductions and transaction revenue
     * @param start start date
     * @param end end date
     * @return Total revenue for this gas station in the given time period
     */
    public double calculateStationRev(Date start, Date end) {
        double rev = 0;

        try {
            rev += getStationTransactionRevenue(start,end);
        } catch (SQLException throwables) {
            System.out.println("Error calculating transaction revenue");
            throwables.printStackTrace();
        }

        try {
            rev += getStationEmployeeDeductionRevenue(start,end);
        } catch (SQLException throwables) {
            System.out.println("Error calculating salary revenue");
            throwables.printStackTrace();
        }

        return rev;
    }

    /**
     * Returns the revenue from all transactions at this gas station for the given time frame
     * @param start start date
     * @param end end date
     * @return Total revenue from transactions for this GasStation for the given time frame
     * @throws SQLException if unsuccessful query
     */
    private double getStationTransactionRevenue(Date start, Date end) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT Quantity, Price, SupplierPrice FROM hsnkwamy_GasStation.Transaction, hsnkwamy_GasStation.Item WHERE GasStationID = ? AND Transaction.ItemID = Item.ItemID " +
                "AND Transaction.DateSold BETWEEN ? AND ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, GasStationID);
        ps.setDate(2, start);
        ps.setDate(3, end);

        // Execute query
        ResultSet rs = ps.executeQuery();

        double transactionRevenue = 0;
        // Set attributes for this GasStation

        while (rs.next()) {
            double quantity = (rs.getDouble("Quantity"));
            double price = (rs.getDouble("Price"));
            double supplierPrice = (rs.getDouble("SupplierPrice"));
            transactionRevenue += quantity * (price - supplierPrice);
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return transactionRevenue;
    }

    /**
     * Returns the sum of all Employees' salaries cost to the company at this gas station for the given periood
     * @param start start date
     * @param end end date
     * @return Sum of all employees' salaries cost to the company (negative value)
     * @throws SQLException if unsuccessful query
     */
    private double getStationEmployeeDeductionRevenue(Date start, Date end) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT Salary FROM hsnkwamy_GasStation.Employee WHERE GasStationID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, GasStationID);

        // Execute query
        ResultSet rs = ps.executeQuery();

        double salaryRevenue = 0;
        // Set attributes for this GasStation

        while (rs.next()) {
            double extrapolatedDailyPay = (rs.getDouble("Salary"))/365;
            double millisecondsElapsed =  end.getTime() - start.getTime();
            double daysElapsed = millisecondsElapsed/(8.64*Math.pow(10, 7));
            salaryRevenue -= extrapolatedDailyPay * daysElapsed;
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return salaryRevenue;
    }

    /**
     * Calculates the total Chain revenue for the given time period - this includes employee salaries and transaction revenues
     * @param start start date
     * @param end end date
     * @return Revenue for the entire Gas Station Chain
     */
    public static double calculateChainRev(Date start, Date end) {
        double rev = 0;

        try {
            rev += getAllTransactionRevenue(start, end);
        } catch (SQLException throwables) {
            System.out.println("Error calculating transaction revenue");
            throwables.printStackTrace();
        }

        try {
            rev += getAllEmployeeDeductionRevenue(start, end);
        } catch (SQLException throwables) {
            System.out.println("Error calculating salary revenue");
            throwables.printStackTrace();
        }

        return rev;
    }

    /**
     * Returns the sum of cost of all employees' salaries in the given time period in the Gas Station Chain
     * @param start start date
     * @param end end date
     * @return the sum of all employees' salaries (a negative value)
     * @throws SQLException if unsuccessful queries
     */
    private static double getAllEmployeeDeductionRevenue(Date start, Date end) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT Salary FROM hsnkwamy_GasStation.Employee";
        PreparedStatement ps = conn.prepareStatement(stationQuery);

        // Execute query
        ResultSet rs = ps.executeQuery();

        double salaryRevenue = 0;
        // Set attributes for this GasStation

        while (rs.next()) {
            double extrapolatedDailyPay = (rs.getDouble("Salary"))/365;
            double millisecondsElapsed =  end.getTime() - start.getTime();
            double daysElapsed = millisecondsElapsed/(8.64*Math.pow(10, 7));
            salaryRevenue -= extrapolatedDailyPay * daysElapsed;
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return salaryRevenue;
    }

    /**
     * Returns the total revenue from all transactions in the gas station chain for the given time period
     * @param start start date
     * @param end end date
     * @return sum of revenue from all transactions in the chain
     * @throws SQLException if unsuccessful query
     */
    private static double getAllTransactionRevenue(Date start, Date end) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT Quantity, Price, SupplierPrice FROM hsnkwamy_GasStation.Transaction, hsnkwamy_GasStation.Item WHERE Transaction.ItemID = Item.ItemID " +
                "AND Transaction.DateSold BETWEEN ? AND ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setDate(1, start);
        ps.setDate(2, end);

        // Execute query
        ResultSet rs = ps.executeQuery();

        double transactionRevenue = 0;
        // Set attributes for this GasStation

        while (rs.next()) {
            double quantity = (rs.getDouble("Quantity"));
            double price = (rs.getDouble("Price"));
            double supplierPrice = (rs.getDouble("SupplierPrice"));
            transactionRevenue += quantity * (price - supplierPrice);
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return transactionRevenue;
    }

    /**
     * Returns a string representation of the schedule for this gas station
     * @return Ready-To-Print schedule as String
     * @throws SQLException if unsuccessful query
     */
    public String gasStationScheduleString() throws SQLException {
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
