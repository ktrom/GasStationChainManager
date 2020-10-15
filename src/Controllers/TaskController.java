package Controllers;

import GasStation.Employee;
import GasStation.Task;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Handles database operations for the Task class]
 */
public class TaskController {

    /**
     * Creates and adds a task to the database
     * @param gasStationID ID of task's gas station
     * @param IDOfEmployee ID of employee to complete task
     * @param taskDescription Description of the task
     * @return true if success creation & saving of the Task, false otherwise
     */
    public boolean createTask(int gasStationID, int IDOfEmployee, String taskDescription) {
        Task task = new Task(gasStationID, IDOfEmployee, taskDescription);
        task.create();
        return true;
    }
}
