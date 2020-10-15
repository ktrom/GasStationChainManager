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
    public Employee(int EmployeeID) {
        // Instantiate gas station id
        this.EmployeeID = EmployeeID;
    }

    /**
     * Create a new Employee.
     */
    public Employee(int GasStationID, String Name, String SSN, Double Salary, String Department, EmployeePosition EmployeePosition, Date StartDate) {
        // Instantiate instance variables
        this.GasStationID = GasStationID;
        this.Name = Name;
        this.SSN = SSN;
        this.Salary = Salary;
        this.Department = Department;
        this.EmployeePosition = EmployeePosition;
        this.StartDate = StartDate;
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

    public void setGasStationID(int GasStationID) {
        this.GasStationID = GasStationID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public void setSalary(Double Salary) {
        this.Salary = Salary;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public void setEmployeePosition(EmployeePosition EmployeePosition) {
        this.EmployeePosition = EmployeePosition;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    /**
     * Pull changes to this Employee.
     *
     * @return true if successful, false otherwise
     */
    public boolean pull() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Employee WHERE EmployeeID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.EmployeeID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return false;
        }

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

        return true;
    }

    /**
     * Push Employee changed fields to the database.
     *
     * @return true if push successful, false otherwise
     */
    public boolean push() throws SQLException {
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
     *
     * @return true if successful, false otherwise
     */
    public boolean create() throws SQLException {
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
        ps.setInt(6, this.EmployeePosition.ordinal());
        ps.setDate(7, this.StartDate);

        // Execute insert
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            // Insert failed, duplicate row
            return false;
        }

        // Set the EmployeeID
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        this.EmployeeID = rs.getInt(1);

        // Close opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }
}
