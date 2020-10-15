package Controllers;

import GasStation.Employee;
import GasStation.EmployeeDepartment;
import GasStation.EmployeePosition;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class HiringManagerController {

    private Employee newHire = null;

    private int EmployeeID;
    private int GasStationID;
    private String Name;
    private String SSN;
    private Double Salary;
    private String Department;
    private EmployeePosition EmployeePosition;
    private Date StartDate;


    private void printOperations(){
        System.out.println("\n");
        // For each operation type the next number: operation
        System.out.println("Operations: ");
        System.out.println("0. Hire Employee");
    }

    private void printDepartments() {
        System.out.println("\n");
        System.out.println("Departments: ");
        System.out.println("0. " + EmployeeDepartment.Service);
        System.out.println("1. " + EmployeeDepartment.Managerial);
    }

    private void printPositions() {
        System.out.println("\n");
        System.out.println("Positions: ");
        if(this.Department.equals(EmployeeDepartment.Service.toString())){
            System.out.println("0. " + EmployeePosition.ATTENDANT);
        } else if(this.Department.equals(EmployeeDepartment.Managerial.toString())) {
            System.out.println("0. " + EmployeePosition.MANAGER);
            System.out.println("1. " + EmployeePosition.HIRING_MANGER);
        }
    }

    private boolean getDepartment(Scanner s){
        System.out.println("\n\n");
        while(true){
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

    private boolean getPosition(Scanner s){
        System.out.println("\n\n");
        while(true){
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
                    this.EmployeePosition = GasStation.EmployeePosition.HIRING_MANGER;
                    return true;
                }
            }
            System.out.println("Sorry, that position is not available.  Select from the list of available options");
        }
    }

    private void registerEmployee(Scanner s, int managerID) throws SQLException {
        Employee hiringManager = new Employee(managerID);
        hiringManager.pull();

        this.GasStationID = hiringManager.getGasStationID();
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

    }

    private boolean hireEmployee(Scanner s, int managerId) throws SQLException {
        getDepartment(s);
        getPosition(s);
        registerEmployee(s, managerId);
        return true;
    }

}
