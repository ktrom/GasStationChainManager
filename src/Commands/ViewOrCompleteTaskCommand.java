package Commands;

import Controllers.EmployeeController;
import Controllers.TaskController;
import GasStation.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class ViewOrCompleteTaskCommand implements Command {
    private final static String description = "View/Complete Tasks";
    private int attendantID;

    public ViewOrCompleteTaskCommand(int attendantID){
        this.attendantID = attendantID;
    }
    /**
     * Allows the attendant to complete a task assigned to them
     */
    private void completeTask(){
        Scanner scan = new Scanner(System.in);
        EmployeeController ec = new EmployeeController();
        ArrayList<Task> employeeTasks = ec.getTasks(attendantID);
        System.out.println("Enter Task # to complete or -1 to exit");
        for(int i = 0; i <  employeeTasks.size(); i++){
            System.out.println("Task "+ (i + 1) + ": " + employeeTasks.get(i).getTaskDescription());
        }
        int choice = scan.nextInt() - 1;

        if(choice > -1) {
            TaskController tc = new TaskController();
            tc.deleteTask(employeeTasks.get(choice).getTaskID());
        }
    }

    @Override
    public void execute() {
        completeTask();
    }

    @Override
    public String description() {
        return description;
    }
}
