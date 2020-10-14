package GasStation;

import java.sql.*;

public class Employee {

    /**
     * Employee ID.
     */
    private int EmployeeID;

    /**
     * Gas Station this Employee works at.
     */
    private int GasStationID;

    /**
     * Employee name.
     */
    private String Name;

    /**
     * Employee SSN.
     */
    private String SSN;

    /**
     * Employee yearly salary.
     */
    private Double Salary;

    /**
     * Employee department.
     */
    private String Department;

    /**
     * Employee position.
     */
    private EmployeePosition EmployeePosition;

    /**
     * Employee start date in position.
     */
    private Date StartDate;

    /**
     * Get an existing Employee by id.
     *
     * @param EmployeeID given EmployeeID
     */
    public Employee(int EmployeeID) throws SQLException {
        // Instantiate gas station id
        this.EmployeeID = EmployeeID;

        // Pull all data to this object
        this.pull();
    }

    /**
     * Create a new Employee.
     */
    public Employee(int GasStationID, String Name, String SSN, Double Salary, String Department, EmployeePosition EmployeePosition, Date StartDate) throws SQLException {
        // Instantiate instance variables
        this.GasStationID = GasStationID;
        this.Name = Name;
        this.SSN = SSN;
        this.Salary = Salary;
        this.Department = Department;
        this.EmployeePosition = EmployeePosition;
        this.StartDate = StartDate;

        // Insert new row
        this.create();
    }

    public int getEmployeeID() {
        return this.EmployeeID;
    }

    public int getGasStationID() {
        return this.GasStationID;
    }

    public String getName() {
        return this.Name;
    }

    public String getSSN() {
        return this.SSN;
    }

    public Double getSalary() {
        return this.Salary;
    }

    public String getDepartment() {
        return this.Department;
    }

    public EmployeePosition getEmployeePosition() {
        return this.EmployeePosition;
    }

    public Date getStartDate() {
        return this.StartDate;
    }

    public boolean setGasStationID(int GasStationID) throws SQLException {
        this.GasStationID = GasStationID;
        return this.push();
    }

    public boolean setName(String Name) throws SQLException {
        this.Name = Name;
        return this.push();
    }

    public boolean setSSN(String SSN) throws SQLException {
        this.SSN = SSN;
        return this.push();
    }

    public boolean setSalary(Double Salary) throws SQLException {
        this.Salary = Salary;
        return this.push();
    }

    public boolean setDepartment(String Department) throws SQLException {
        this.Department = Department;
        return this.push();
    }

    public boolean setEmployeePosition(EmployeePosition EmployeePosition) throws SQLException {
        this.EmployeePosition = EmployeePosition;
        return this.push();
    }

    public boolean setStartDate(Date StartDate) throws SQLException {
        this.StartDate = StartDate;
        return this.push();
    }

    /**
     * Pull changes to this Employee.
     */
    private void pull() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Employee WHERE EmployeeID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.EmployeeID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        rs.next();

        // Set attributes for this GasStation
        this.GasStationID = rs.getInt("GasStationID");
        this.Name = rs.getString("Name");
        this.SSN = rs.getString("SSN");
        this.Salary = rs.getDouble("Salary");
        this.Department = rs.getString("Department");
        this.EmployeePosition = EmployeePosition.valueOf(rs.getString("EmployeePosition").toUpperCase());
        this.StartDate = rs.getDate("StartDate");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();
    }

    /**
     * Push Employee changed fields to the database.
     *
     * @return true if push successful, false otherwise
     */
    private boolean push() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "UPDATE hsnkwamy_GasStation.Employee SET GasStationID = ?, Name = ?, SSN = ?, Salary = ?, Department = ?, EmployeePosition = ?, StartDate = ? WHERE EmployeeID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, this.GasStationID);
        ps.setString(2, this.Name);
        ps.setString(3, this.SSN);
        ps.setDouble(4, this.Salary);
        ps.setString(5, this.Department);
        ps.setString(6, this.EmployeePosition.name());
        ps.setDate(7, this.StartDate);
        ps.setInt(8, this.EmployeeID);

        // Execute the update
        int rowsAffected = ps.executeUpdate();

        // Close all opened streams
        ps.close();
        conn.close();

        return rowsAffected == 1;
    }

    /**
     * Create a new Employee in the database.
     */
    private void create() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "INSERT INTO hsnkwamy_GasStation.Employee SET GasStationID = ?, Name = ?, SSN = ?, Salary = ?, Department = ?, EmployeePosition = ?, StartDate = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, this.GasStationID);
        ps.setString(2, this.Name);
        ps.setString(3, this.SSN);
        ps.setDouble(4, this.Salary);
        ps.setString(5, this.Department);
        ps.setString(6, this.EmployeePosition.name());
        ps.setDate(7, this.StartDate);

        // Execute insert
        ps.executeUpdate();

        // Set the GasStationID
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        this.EmployeeID = rs.getInt(1);

        // Close opened streams
        rs.close();
        ps.close();
        conn.close();
    }
}
