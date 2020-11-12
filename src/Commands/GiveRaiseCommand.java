package Commands;

import Controllers.EmployeeController;
import Controllers.GasStationController;
import Controllers.TaskController;
import GasStation.Employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class GiveRaiseCommand implements Command {
    private final static String description = "Give Raise";
    private int managerID;
    public GiveRaiseCommand(int managerID) {
        this.managerID = managerID;
    }

    @Override
    public void execute() {
        EmployeeController ec = new EmployeeController();
        Employee manager = ec.getEmployee(managerID);

        int gasStationID = ec.getGasStationID(managerID);

        Scanner scan = new Scanner(System.in);

        GasStationController gsc = new GasStationController();
        ArrayList<Employee> availableEmployees = gsc.getEmployees(gasStationID);

        if (availableEmployees.size() == 0){
            System.out.print("No employees in system!");
        }
        else {
            System.out.println("Which Employee would you like to give a raise to?");
            Iterator<Employee> i = availableEmployees.iterator();
            int j = 1;
            while (i.hasNext()) {
                Employee nextEmployee = i.next();
                System.out.println(j + ": " + nextEmployee.getName());
                j++;
            }
            int selectedIndex = scan.nextInt() - 1;

            Employee selectedEmployee = availableEmployees.get(selectedIndex);

            int employeeId = selectedEmployee.getEmployeeID();
            double employeeSalary = selectedEmployee.getSalary();
            scan.nextLine();

            System.out.println("The employee's current hourly wage is " + employeeSalary);
            System.out.println("What would you like to set their new hourly wage to?");
            System.out.print("$");
            double newSalary = scan.nextDouble();

            if(newSalary > employeeSalary) {
                selectedEmployee.setSalary(newSalary);
                selectedEmployee.push();
                System.out.println("Salary updated!\n");
            }
            else{
                System.out.println("Entered wage lower than current wage! Raise failed.\n");
            }
        }

    }

    @Override
    public String description() {
        return description;
    }
}
