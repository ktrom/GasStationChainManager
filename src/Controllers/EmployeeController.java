package Controllers;

import DatabaseClasses.DatabaseSupport;
import GasStation.Employee;
import GasStation.EmployeePosition;
import GasStation.Task;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller for handling database operations relating to Employee
 */
public class EmployeeController {

    /**
     * Returns the Employee object from database where employeeID matches
     * @param employeeID ID of the employee
     * @return Employee object with employeeID
     */
    public Employee getEmployee(int employeeID){
        Employee g = new Employee(employeeID);
        g.pull();
        return g;
    }

    /**
     * Returns gasStationID of the gas station an Employee works at
     * @param employeeID ID of the employee
     * @return gasStationID of employee's gas station
     */
    public int getGasStationID(int employeeID){
        Employee g = new Employee(employeeID);
        g.pull();
        return g.getGasStationID();
    }

    /**
     * Retrieves Employee's position given their ID
     * @param employeeID ID of desired Employee
     * @return EmployeePosition object of the Employee's position
     */
    public EmployeePosition getEmployeePosition(int employeeID){
        Employee g = new Employee(employeeID);
        g.pull();
        return g.getEmployeePosition();
    }

    /**
     * Creates a new Employee and saves to the database
     * @param gasStationID ID of gas station employee works at
     * @param name Name of employee
     * @param ssn Social Security Number of employee
     * @param salary Yearly wage of employeee
     * @param department Department employee works in
     * @param position Employee's title
     * @param startDate Employee's first day at this company
     * @return true if successful employee creation
     *         false otherwise
     */
    public boolean createEmployee(int gasStationID, String name, String ssn, double salary, String department, EmployeePosition position, Date startDate){
        Employee e = new Employee(gasStationID, name, ssn, salary, department, position, startDate);
        return e.create();
    }

    /**
     * Returns tasks for given Employee
     * @param employeeID ID of employee
     * @return ArrayList of all tasks assigned to this employee
     */
    public ArrayList<Task> getTasks(int employeeID){
        try {
            return DatabaseSupport.getEmployeeTasks(employeeID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
