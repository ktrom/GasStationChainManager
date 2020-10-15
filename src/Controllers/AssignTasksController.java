package Controllers;

import GasStation.Employee;
import GasStation.GasStation;
import GasStation.Task;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class AssignTasksController {

    /**
     * assigns an employee a task at the given manager's gas station
     * @param managerId
     */
    public void assignTask(int managerId) {
        Scanner s = new Scanner(System.in);

        Employee manager = new Employee(managerId);

        try {
            manager.pull();
        } catch (SQLException throwables) {
            System.out.println("Error pulling manager");
            throwables.printStackTrace();
        }

        GasStation currentStation = new GasStation(manager.getGasStationID());

        ArrayList<Employee> availableEmployees = new ArrayList<Employee>();
        try {
            availableEmployees = currentStation.getEmployees();
        } catch (SQLException throwables) {
            System.out.println("Error finding available employees");
            throwables.printStackTrace();
        }

        System.out.println("Which Employee would you like to assign a task to?");
        Iterator<Employee> i = availableEmployees.iterator();
        int j = 1;
        while(i.hasNext()){
            Employee nextEmployee = i.next();
            System.out.println(j + ": " + nextEmployee.getName());
            j++;
        }
        int selectedIndex = s.nextInt() - 1;
        Employee selectedEmployee = availableEmployees.get(selectedIndex);

        int employeeId = selectedEmployee.getEmployeeID();

        String taskDesc = null;

        System.out.println("What task would you like to assign the employee?");
        printAvailableTasks();
//        taskDesc = the description of whatever task was indicated;


        Task task = new Task(currentStation.getGasStationID(), employeeId, taskDesc);
        try {
            task.create();
        } catch (SQLException throwables) {
            System.out.println("Task addition failed.");
            throwables.printStackTrace();
        }
    }

    /**
     * Outputs all currently unassigned tasks
     */
    private void printAvailableTasks() {
    }
}
