package Controllers;

import DatabaseClasses.DatabaseSupport;
import GasStation.Employee;
import GasStation.EmployeeDepartment;
import GasStation.EmployeePosition;
import GasStation.GasStation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

/**-
 * Controller used by an employee with the position Hiring Manager manage employees
 * Currently the only operation is hiring employees
 */
public class HiringManagerController {

    /**
     * Adds an employee to the database
     * @param GasStationID ID of gas station
     * @param Name Name of employee
     * @param SSN Social Security Number of employee
     * @param salary Yearly wage of employee
     * @param department Department employee works in
     * @param employeePosition Employee's job title
     * @param startDate The employee's first day with the company
     * @return Employee
     */
    public Employee hireEmployee(int GasStationID, String Name, String SSN, Double salary, String department, EmployeePosition employeePosition, Date startDate) throws SQLException {
        Employee newHire = new Employee(GasStationID, Name, SSN, salary, department, employeePosition, startDate);
        newHire.create();
        return newHire;
    }

    /**
     * Removes a employee from the database.
     * Used when an employee completes a task
     * @param employeeID ID of omployee to be removed
     * @return true if successful removal, false otherwise
     */
    public boolean deleteEmployee(int employeeID){
        try {
            DatabaseSupport.deleteEmployee(employeeID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }
}
