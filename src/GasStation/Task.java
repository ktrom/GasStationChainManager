package GasStation;

import Interfaces.Model;

import java.sql.*;

public class Task implements Model {

    private int TaskID;

    private int GasStationID;

    private int EmployeeID;

    private String TaskDescription;

    public int getGasStationID() {
        return GasStationID;
    }

    public int getTaskID() {
        return TaskID;
    }

    public void setTaskID(int taskID) {
        TaskID = taskID;
    }

    public void setGasStationID(int gasStationID) {
        GasStationID = gasStationID;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public String getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        TaskDescription = taskDescription;
    }

    public Task(int gasStationID, int employeeID, String taskDescription) {
        GasStationID = gasStationID;
        EmployeeID = employeeID;
        TaskDescription = taskDescription;
    }

    public Task(int taskID){
        TaskID = taskID;
    }

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
        String stationQuery = "SELECT * FROM hsnkwamy_GasStation.Task WHERE TaskID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.TaskID);

        // Execute query
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return false;
        }

        // Set attributes for this GasStation
        this.GasStationID = rs.getInt("GasStationID");
        this.EmployeeID= rs.getInt("EmployeeId");
        this.TaskDescription = rs.getString("TaskDescription");

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }

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
        String stationQuery = "UPDATE hsnkwamy_GasStation.Task SET GasStationId = ?, EmployeeID = ?, TaskDescription = ? WHERE TaskID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, this.GasStationID);
        ps.setInt(2, this.EmployeeID);
        ps.setString(3, this.TaskDescription);

        // Execute the update
        int rowsAffected = ps.executeUpdate();

        // Close all opened streams
        ps.close();
        conn.close();

        return rowsAffected == 1;
    }

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
        String stationQuery = "INSERT INTO hsnkwamy_GasStation.Task SET GasStationId = ?, EmployeeId = ?, TaskDescription = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, this.GasStationID);
        ps.setInt(2, this.EmployeeID);
        ps.setString(3, this.TaskDescription);

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
        this.TaskID = rs.getInt(1);

        // Close opened streams
        rs.close();
        ps.close();
        conn.close();

        return true;
    }

}
