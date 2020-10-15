package UI;

import Controllers.EmployeeController;
import Controllers.GasStationController;
import Controllers.ScheduleController;
import Controllers.TaskController;
import GasStation.Employee;
import GasStation.Task;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * User Interface class for a manager
 */
public class ManagerUI {

    /**
     * Handles display and input for manager options
     */
    public void handleManager(int managerID) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Logged in as Manager\n");
        int option = 1;

        while(option == 1 || option == 2 || option == 3 || option == 4) {
            System.out.println("Select an option or enter -1 to quit:");
            System.out.println("1. Schedule Employees");
            System.out.println("2. Assign Tasks");
            System.out.println("3. Print Out Schedule");
            System.out.println("4. Print Out Tasks");
            option = scan.nextInt();
            if (option == 1) {
                scheduleEmployee(managerID);
            } else if (option == 2) {
                assignTask(managerID);
            }
            else if(option == 3){
                printSchedule(managerID);
            }
            else if(option == 4){
                printTasks(managerID);
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

        GasStationController gsc = new GasStationController();
        ArrayList<Employee> availableEmployees = gsc.getEmployees(gasStationID);

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

        ScheduleController sc = new ScheduleController();
        sc.scheduleEmployee(gasStationID, employeeId, d, shift);

        printSchedule(managerID);
    }

    /**
     * Procedure for a manager to assign a task to an employee
     * @param managerID the manager's (actor's) ID
     */
    private void assignTask(int managerID){

        EmployeeController ec = new EmployeeController();
        Employee manager = ec.getEmployee(managerID);

        int gasStationID = ec.getGasStationID(managerID);

        Scanner scan = new Scanner(System.in);

        int IDofAssignedEmployee;
        System.out.println("What is the ID of the employee you would like to assign? ");
        IDofAssignedEmployee = scan.nextInt();

        scan.nextLine();
        String taskDescription;
        System.out.println("Give a brief description of the task: ");
        taskDescription = scan.nextLine();

        TaskController tc = new TaskController();
        tc.createTask(gasStationID, IDofAssignedEmployee, taskDescription);
    }

    /**
     * Prints the current schedule for the manager's gas station
     * @param managerID Manager's (Actor's) ID
     */
    private void printSchedule(int managerID){
        EmployeeController ec = new EmployeeController();
        int gasStationId = ec.getGasStationID(managerID);

        ScheduleController sc = new ScheduleController();
        System.out.println("Schedule");
        System.out.println(sc.gasStationSchedule(gasStationId));
    }

    private void printTasks(int managerIO){
        EmployeeController ec = new EmployeeController();
        int gasSTationID = ec.getGasStationID(managerIO);

        TaskController tc = new TaskController();
        System.out.println("Tasks");
        System.out.println(tc.getGasStationTasks(gasSTationID));
    }
}


