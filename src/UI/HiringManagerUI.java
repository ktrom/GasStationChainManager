package UI;

import Controllers.HiringManagerController;
import GasStation.Employee;
import GasStation.EmployeeDepartment;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;
/**-
 * UI used by an employee with the position Hiring Manager manage employees
 * Currently the only operation is hiring employees
 */
public class HiringManagerUI {
    private Employee employee = null; // Our new Employee Object if the "Hire Employee" Operation is given

    // variables needed to make new Employee
    private int EmployeeID;
    private int GasStationID;
    private String Name;
    private String SSN;
    private Double Salary;
    private String Department;
    private GasStation.EmployeePosition EmployeePosition;
    private Date StartDate;

    /**
     * Sets this.ssn to valid user input for ssn in form #########
     * @param s
     */
    private void getValidSSN(Scanner s) {
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
    }

    /**
     * Sets this.startDate to a valid user input date in the
     * for yyyy-mm-dd
     * @param s
     */
    private void getStartValidDate(Scanner s) {
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
    }

    /**
     * Lists the operations the hiring manager has to choose from
     */
    private void printOperations() {
        System.out.println("\n");
        // For each operation type the next number: operation
        System.out.println("Operations: ");
        System.out.println("0. Hire Employee");
        System.out.println("1. Remove Employee");
        System.out.println("2. Update Employee");
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
        System.out.println("\n");
        //runs until successful department input
        while (true) {
            printDepartments();
            System.out.print("Type number of employees's department: ");
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
        System.out.println("\n");
        //runs until successful position input
        while (true) {
            printPositions();
            System.out.print("Type number of employees's position: ");
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
        System.out.print("Enter Employee's name: ");
        this.Name = s.next();
        getValidSSN(s);
        System.out.print("Enter Employee's salary: ");
        this.Salary = s.nextDouble();

        getStartValidDate(s);

        HiringManagerController hmc = new HiringManagerController();
        employee = hmc.hireEmployee(this.GasStationID, this.Name, this.SSN, this.Salary, this.Department, this.EmployeePosition, this.StartDate);

        System.out.println("\nEmployee Registered: ");
        reportEmployeeInfo(false);
    }

    /**
     * Prints the newly registered employee's information to the screen
     */
    private void reportEmployeeInfo(boolean number){
        //return new hire info
        if(number){
            System.out.println("1. Department: " + employee.getDepartment());
            System.out.println("2. Position: " + employee.getEmployeePosition());
            System.out.println("3. Gas Station ID: " + employee.getGasStationID());
            System.out.println("4. Name: " + employee.getName());
            System.out.println("5. SSN: " + employee.getSSN());
            System.out.println("6. Salary: " + employee.getSalary());
            System.out.println("7. Start Date:" + employee.getStartDate());
        } else {
            System.out.println("EmployeeId: " + employee.getEmployeeID());
            System.out.println("Department: " + employee.getDepartment());
            System.out.println("Position: " + employee.getEmployeePosition());
            System.out.println("Gas Station ID: " + employee.getGasStationID());
            System.out.println("Name: " + employee.getName());
            System.out.println("SSN: " + employee.getSSN());
            System.out.println("Salary: " + employee.getSalary());
            System.out.println("Start Date:" + employee.getStartDate());
        }
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

    /**
     * Operation that takes an employee to remove from the database
     * @param s is a Scanner for user input
     * @return
     * @throws SQLException
     */
    private boolean removeEmployee(Scanner s) throws SQLException {
        System.out.println("\n Enter Employee ID you wish to remove: ");
        int employeeID = s.nextInt();
        HiringManagerController hmc = new HiringManagerController();
        Employee e = new Employee(employeeID);
        e.pull();
        while(true){
            System.out.println("Would you like to remove " + e.getName() + " at gas station " + e.getGasStationID() + "? ('y' or 'n'): ");
            String yOrN = s.next();
            if(yOrN.equals("y")){
                hmc.deleteEmployee(employeeID);
                System.out.println(e.getName() + " has been removed.");
                return true;
            } else if (yOrN.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid Answer");
            }
        }

    }

    /**
     * Operation that takes an employee to update info on into the database
     * @param s is a Scanner for user input
     * @return
     * @throws SQLException
     */
    private boolean updateEmployee(Scanner s) throws SQLException {
        System.out.println("\n Enter Employee ID you wish to update: ");
        int employeeID = s.nextInt();
        HiringManagerController hmc = new HiringManagerController();
        Employee e = new Employee(employeeID);
        e.pull();
        this.employee = e;
        reportEmployeeInfo(true);
        while(true){
            System.out.println("Type number indicating what you wish to change");
            int numInput = s.nextInt();
            if(numInput == 1){
                getDepartment(s);
                e.setDepartment(this.Department);
                e.push();
                return true;
            } else if(numInput == 2) {
                getPosition(s);
                e.setEmployeePosition(this.EmployeePosition);
                e.push();
                return true;
            } else if(numInput == 3) {
                System.out.println("Enter employee's gas station id: ");
                e.setGasStationID(s.nextInt());
                e.push();
                return true;
            } else if(numInput == 4) {
                System.out.println("Enter employee's name: ");
                e.setName(s.next());
                e.push();
                return true;
            } else if(numInput == 5) {
                getValidSSN(s);
                e.setSSN(this.SSN);
                e.push();
                return true;
            } else if(numInput == 6) {
                System.out.println("Enter employee's salary: ");
                e.setSalary(s.nextDouble());
                e.push();
                return true;
            } else if(numInput == 7) {
                getStartValidDate(s);
                e.setStartDate(this.StartDate);
                e.push();
                return true;
            } else {
                System.out.println("Invalid number, enter 1-7");
            }
        }

    }

    /**
     * All available operations a hiring manager can perform
     * Manager types number corresponding to the operation they wish to perform
     * Manager can perfor operations until they quit by typing -1
     * @param managerId
     * @throws SQLException
     */
    public void handleHiringManager(int managerId) throws SQLException {
        Scanner s = new Scanner(System.in);
        // Check desired input against available operations
        boolean validInput = false;
        System.out.println("Logged in as Hiring Manager\n");

        while(!validInput){
            printOperations();
            System.out.print("Select an option or enter -1 to quit: ");
            int input = s.nextInt(); // user input integer
            if(input == -1){
                return;
            }
            if(input == 0){
                hireEmployee(s, managerId);
                validInput = true;
            } else if(input == 1) {
                removeEmployee(s);
            } else if(input == 2) {
                updateEmployee(s);
            } else {
                // input does not match any operation
                System.out.println("Sorry, that operation is not available. Select from the list of available options.");
            }
        }
        s.close();
    }
}

