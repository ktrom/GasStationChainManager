package Commands;

import Controllers.EmployeeController;
import Controllers.TaskController;

public class PrintTasksCommand implements Command{
    private final static String description = "Print Tasks";
    private int managerID;

    public PrintTasksCommand(int managerID){
        this.managerID = managerID;
    }
    /**
     * Prints out all tasks for this manager's gas station
     */
    private void printTasks(){
        EmployeeController ec = new EmployeeController();
        int gasSTationID = ec.getGasStationID(managerID);

        TaskController tc = new TaskController();
        System.out.println("Tasks");
        System.out.println(tc.getGasStationTasks(gasSTationID));
    }

    @Override
    public void execute() {
        printTasks();
    }

    @Override
    public String description() {
        return description;
    }
}
