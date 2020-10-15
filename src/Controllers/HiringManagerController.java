package Controllers;

import GasStation.Employee;
import GasStation.EmployeeDepartment;
import GasStation.EmployeePosition;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Controller used by an employee with the position Hiring Manager manage employees
 * Currently the only operation is hiring employees
 */
public class HiringManagerController {

    private Employee newHire = null; // Our new Employee Object if the "Hire Employee" Operation is given

    // variables needed to make new Employee
    private int EmployeeID;
    private int GasStationID;
    private String Name;
    private String SSN;
    private Double Salary;
    private String Department;
    private EmployeePosition EmployeePosition;
    private Date StartDate;

    /**
     * Lists the operations the hiring manager has to choose from
     */
    private void printOperations() {
        System.out.println("\n");
        // For each operation type the next number: operation
        System.out.println("Operations: ");
        System.out.println("0. Hire Employee");
    }

    /**
     * Lists the departments the hiring manager can add new employees to
     * Hiring manager may not hire to the Executive department
     */
    private void printDepartments() {
        System.out.println("\n");
        System.out.println("Departments: ");
        System.out.println("0. " + EmployeeDepartment.Service);
        System.out.println("1. " + EmployeeDepartment.Managerial);
    }

    /**
     * Lists the positions available under the given department
     * Service: Attendant
     * Managerial: Manager or Hiring Manager
     */
    private void printPositions() {
        System.out.println("\n");
        System.out.println("Positions: ");
        if(this.Department.equals(EmployeeDepartment.Service.toString())){
            System.out.println("0. " + EmployeePosition.ATTENDANT);
        } else if(this.Department.equals(EmployeeDepartment.Managerial.toString())) {
            System.out.println("0. " + EmployeePosition.MANAGER);
            System.out.println("1. " + EmployeePosition.HIRING_MANAGER);
        }
    }

    /**
     * Gets user input to select new hire department
     * User types number corresponding to department
     * @param s is a Scanner for user input
     * @return true when complete
     */
    private boolean getDepartment(Scanner s) {
        System.out.println("\n\n");
        //runs until successful department input
        while (true) {
            printDepartments();
            System.out.print("Type number of new hire's department: ");
            int input = s.nextInt();
            if(input == 0){
                this.Department = EmployeeDepartment.Service.toString();
                return true;
            } else if (input == 1){
                this.Department = EmployeeDepartment.Managerial.toString();
                return true;
            } else {
                System.out.println("Sorry, that department is not available.  Select from the list of available options");
            }
        }
    }

    /**
     * Gets user input to select new hire position based department selected
     * User types number corresponding to position
     * @param s is a Scanner for user input
     * @return true when complete
     */
    private boolean getPosition(Scanner s) {
        System.out.println("\n\n");
        //runs until successful position input
        while (true) {
            printPositions();
            System.out.print("Type number of new hire's position: ");
            int input = s.nextInt();
            if(this.Department.equals(EmployeeDepartment.Service.toString())){
                if(input == 0){
                    this.EmployeePosition = GasStation.EmployeePosition.ATTENDANT;
                    return true;
                }
            } else if (this.Department.equals(EmployeeDepartment.Managerial.toString())){
                if(input == 0){
                    this.EmployeePosition = GasStation.EmployeePosition.MANAGER;
                    return true;
                } else if( input == 1){
                    this.EmployeePosition = GasStation.EmployeePosition.HIRING_MANAGER;
                    return true;
                }
            }
            System.out.println("Sorry, that position is not available.  Select from the list of available options");
        }
    }

    /**
     * Takes user input from the hiring manager about the new hire's personal info
     * The hewHire Employee object is filled with the given info and the Employee is
     * created into the database
     * @param s is a Scanner for user input
     * @param managerID is the ID of the hiring manager using the controller
     * @throws SQLException
     */
    private void registerEmployee(Scanner s, int managerID) throws SQLException {
        Employee hiringManager = new Employee(managerID);
        hiringManager.pull();

        // set new hire manager to hiring manager gas station id
        this.GasStationID = hiringManager.getGasStationID();

        // get employee personal info
        System.out.println("\n\n");
        System.out.print("Enter Employee's name: ");
        this.Name = s.next();
        boolean validSSN = false;
        while(!validSSN){
            System.out.print("Enter Employee's Social Security Number: ");
            String ssn = s.next();
            if(ssn.length() == 9){
                this.SSN = ssn;
                validSSN = true;
            } else {
                System.out.println("Sorry, that's an invalid ssn. Type it as #########");
            }
        }
        System.out.print("Enter Employee's salary: ");
        this.Salary = s.nextDouble();

        boolean validDate = false;
        while(!validDate){
            System.out.print("Enter Employee's start date(yyyy-mm-dd): ");
            String dateString = s.next();
            String[] date = dateString.split("-");
            if(date.length == 3 && Integer.parseInt(date[0]) > 999 && Integer.parseInt(date[0]) < 10000 && Integer.parseInt(date[1]) > 0 && Integer.parseInt(date[1]) < 13 && Integer.parseInt(date[2]) > 0 && Integer.parseInt(date[2]) < 32) {
                this.StartDate = Date.valueOf(dateString);
                validDate = true;
            } else {
                System.out.println("Sorry, that's an invalid date. Type it as yyyy-mm-dd");
            }
        }

        newHire = new Employee(this.GasStationID, this.Name, this.SSN, this.Salary, this.Department, this.EmployeePosition, this.StartDate);
        newHire.create();

        //return new hire info
        System.out.println("\nEmployee Registered: ");
        System.out.println("EmployeeId: " + newHire.getEmployeeID());
        System.out.println("Department: " + newHire.getDepartment());
        System.out.println("Position: " + newHire.getEmployeePosition());
        System.out.println("Gas Station ID: " + newHire.getGasStationID());
        System.out.println("Name: " + newHire.getName());
        System.out.println("SSN: " + newHire.getSSN());
        System.out.println("Salary: " + newHire.getSalary());
        System.out.println("Start Date:" + newHire.getStartDate());
    }

    /**
     * All available operations a hiring manager can perform
     * Manager types number corresponding to the operation they wish to perform
     * Manager can perfor operations until they quit by typing -1
     * @param managerId
     * @throws SQLException
     */
    public void hiringManagerOptions(int managerId) throws SQLException {
        System.out.println("\n\n");
        Scanner s = new Scanner(System.in);
        // Check desired input against available operations
        boolean validInput = false;
        while(!validInput){
            printOperations();
            System.out.print("Type number of operation you wish to perform: ");
            int input = s.nextInt(); // user input integer
            if(input == 0){
                hireEmployee(s, managerId);
                validInput = true;
            } else {
                // input does not match any operation
                System.out.println("Sorry, that operation is not available. Select from the list of available options.");
            }
        }
        s.close();
    }

    /**
     * Operation that takes hiring manager input to add a new employee to the database
     * @param s is a Scanner for user input
     * @param managerId is the ID of the hiring manager using the software
     * @return
     * @throws SQLException
     */
    private boolean hireEmployee(Scanner s, int managerId) throws SQLException {
        getDepartment(s);
        getPosition(s);
        registerEmployee(s, managerId);
        return true;
    }

}
