package UI;

import Controllers.EmployeeController;
import Controllers.ScheduleController;
import Controllers.TaskController;
import GasStation.Employee;
import GasStation.GasStation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ManagerUI {

    /**
     * Handles display and input for manager options
     */
    public void handleManager(int managerID) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Logged in as Manager\n");
        int option = 1;

        while(option == 1 || option == 2) {
            System.out.println("Select an option or enter -1 to quit:");
            System.out.println("1. Schedule Employees");
            System.out.println("2. Assign Tasks");


            if (option == 1) {
                scheduleEmployee(managerID);
            } else if (option == 2) {
                assignTask(managerID);
            }
        }
    }

    /**
     * schedules an employee at the given manager's gas station
     *
     * @param managerID The ID of the actor (A Manager)
     */
    private void scheduleEmployee(int managerID) {
        Scanner s = new Scanner(System.in);

        EmployeeController ec = new EmployeeController();
        int gasStationID = ec.getGasStationID(managerID);

        ScheduleController sc = new ScheduleController();
        ArrayList<Employee> availableEmployees = sc.getEmployees(gasStationID);

        System.out.println("Which Employee would you like to schedule?");
        Iterator<Employee> i = availableEmployees.iterator();
        int j = 1;
        while (i.hasNext()) {
            Employee nextEmployee = i.next();
            System.out.println(j + ": " + nextEmployee.getName());
            j++;
        }
        int selectedIndex = s.nextInt() - 1;

        Employee selectedEmployee = availableEmployees.get(selectedIndex);

        int employeeId = selectedEmployee.getEmployeeID();

        String date = "";

        System.out.println("What day would you like to schedule the employee for? (YYYY-MM-DD)");
        date = s.next();

        Date d = Date.valueOf(date);

        System.out.println("What shift would you like to schedule the employee for?");
        System.out.println("1: 7-3");
        System.out.println("2: 3-11");
        System.out.println("3: 11-7");

        System.out.println("Enter 1, 2, or 3: ");
        int shift = s.nextInt();

        sc.createSchedule(gasStationID, employeeId, d, shift);

    }

    private void assignTask(int managerID){

        EmployeeController ec = new EmployeeController();
        Employee manager = ec.getEmployee(managerID);

        int gasStationID = ec.getGasStationID(managerID);

        Scanner scan = new Scanner(System.in);

        int IDofAssignedEmployee;
        System.out.println("What is the ID of the employee you would like to assign? ");
        IDofAssignedEmployee = scan.nextInt();

        String taskDescription;
        System.out.println("Give a brief description of the task: ");
        taskDescription = scan.nextLine();

        TaskController tc = new TaskController();
        tc.createTask(gasStationID, IDofAssignedEmployee, taskDescription);
    }
}


