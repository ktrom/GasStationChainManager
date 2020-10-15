package Controllers;

import GasStation.Employee;
import GasStation.Task;

import java.sql.SQLException;
import java.util.Scanner;

public class TaskController {
    public void assignTask(int EmployeeID){
        Employee manager = new Employee(EmployeeID);

        try {
            manager.pull();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int gasStationID = manager.getGasStationID();

        Scanner scan = new Scanner(System.in);

        int IDofAssignedEmployee;
        System.out.println("What is the ID of the employee you would like to assign? ");
        IDofAssignedEmployee = scan.nextInt();

        String taskDescription;
        System.out.println("Give a brief description of the task: ");
        taskDescription = scan.nextLine();

        Task task = new Task(gasStationID, IDofAssignedEmployee, taskDescription);

        try {
            task.create();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
