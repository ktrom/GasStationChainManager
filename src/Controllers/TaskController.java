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
    public boolean assignTask(int gasStationID, int IDOfEmployee, String taskDescription) {
        Task task = new Task(gasStationID, IDOfEmployee, taskDescription);
        task.create();
        return true;
    }

    /**
     * Returns a String representation of all tasks assigned at this gas station along with
     * who the tasks are assigned to
     * @param gasStationId ID of gas station
     * @return String of Assignee and Task Descriptions for Gas Station
     */
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

    /**
     * Removes a task from the database.
     * Used when an employee completes a task
     * @param taskID ID of task to be removed
     * @return true if successful removal, false otherwise
     */
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
