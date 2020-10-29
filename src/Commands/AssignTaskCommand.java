package Commands;

import Controllers.EmployeeController;
import Controllers.GasStationController;
import Controllers.TaskController;
import GasStation.Employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class AssignTaskCommand implements Command{
    private final static String description = "Assign Task";
    private int managerID;

    public AssignTaskCommand(int managerID){
        this.managerID = managerID;
    }

    /**
     * Procedure for a manager to assign a task to an employee
     */
    private void assignTask(){

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
            System.out.println("Which Employee would you like to assign a task to?");
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

            scan.nextLine();
            String taskDescription;
            System.out.println("Give a brief description of the task: ");
            taskDescription = scan.nextLine();

            TaskController tc = new TaskController();
            tc.assignTask(gasStationID, employeeId, taskDescription);
        }
    }

    @Override
    public void execute() {
        assignTask();
    }

    @Override
    public String description() {
        return description;
    }


}
