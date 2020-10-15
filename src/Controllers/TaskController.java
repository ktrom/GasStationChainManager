package Controllers;

import GasStation.Employee;
import GasStation.Task;

import java.sql.SQLException;
import java.util.Scanner;

public class TaskController {

    public boolean createTask(int gasStationID, int IDOfEmployee, String taskDescription) {
        Task task = new Task(gasStationID, IDOfEmployee, taskDescription);
        task.create();
        return true;
    }
}
