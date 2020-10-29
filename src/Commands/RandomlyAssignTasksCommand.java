package Commands;

import Controllers.EmployeeController;
import Controllers.GasStationController;
import Controllers.TaskController;
import GasStation.Employee;

import java.util.ArrayList;
import java.util.Scanner;

public class RandomlyAssignTasksCommand implements Command{
    private final static String description = "Randomly Assign Tasks";
    private int managerID;

    public RandomlyAssignTasksCommand(int managerID) {
        this.managerID = managerID;
    }
    /**
     * Assigns random tasks between employees
     */
    private void assignRandomTasks(){
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
            System.out.println("Enter as many tasks as you have and they will be randomly distributed evenly among your employees");
            ArrayList<String> descriptions = new ArrayList<String>();
            int count = 1;
            while(count != -1){
                System.out.println("Enter next task: ");
                String description = scan.nextLine();
                descriptions.add(description);
                System.out.println("Enter -1 to exit or enter 1 to enter another task");
                count = scan.nextInt();
                scan.nextLine();
            }
            TaskController tc = new TaskController();
            gsc.assignRandomTasks(gasStationID, descriptions);
            int selectedIndex = scan.nextInt() - 1;
        }
    }

    @Override
    public void execute() {
        assignRandomTasks();
    }

    @Override
    public String description() {
        return description;
    }
}


