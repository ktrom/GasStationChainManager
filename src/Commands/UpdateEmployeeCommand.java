package Commands;

import Controllers.HiringManagerController;
import GasStation.Employee;
import GasStation.EmployeeDepartment;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateEmployeeCommand implements Command{
    private static final String description = "Update Employee";
    private Employee employee = null; // Our new Employee Object if the "Hire Employee" Operation is given
    private int actingEmployeeID;

    // variables needed to make new Employee
    private int EmployeeID;
    private int GasStationID;
    private String Name;
    private String SSN;
    private Double Salary;
    private String Department;
    private GasStation.EmployeePosition EmployeePosition;
    private Date StartDate;

    public UpdateEmployeeCommand(int actingEmployeeID){
        this.actingEmployeeID = actingEmployeeID;
    }
    /**
     * Operation that takes an employee to update info on into the database
     * @return
     * @throws SQLException
     */
    private boolean updateEmployee(){
        Scanner s = new Scanner(System.in);
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


    @Override
    public void execute() {
        updateEmployee();
    }

    @Override
    public String description() {
        return description;
    }
}
