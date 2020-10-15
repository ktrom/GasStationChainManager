package Controllers;

import DatabaseClasses.DatabaseSupport;
import GasStation.Employee;
import GasStation.GasStation;
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

    public String getGasStationTasks(int gasStationId){
        GasStation g = new GasStation(gasStationId);
        g.pull();
        try {
            return DatabaseSupport.gasStationTasksString(g.getGasStationID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean deleteTask(int taskID){
        try {
            DatabaseSupport.deleteTask(taskID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }
}
