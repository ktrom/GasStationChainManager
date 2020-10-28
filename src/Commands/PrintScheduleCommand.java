package Commands;

import Controllers.EmployeeController;
import Controllers.ScheduleController;

public class PrintScheduleCommand implements Command{
    private final static String description = "Print Schedule";
    private int managerID;

    public PrintScheduleCommand(int managerID){
        this.managerID = managerID;
    }

    /**
     * Prints the current schedule for the manager's gas station
     */
    private void printSchedule(){
        EmployeeController ec = new EmployeeController();
        int gasStationId = ec.getGasStationID(managerID);

        ScheduleController sc = new ScheduleController();
        System.out.println("Schedule");
        System.out.println(sc.gasStationSchedule(gasStationId));
    }


    @Override
    public void execute() {
        printSchedule();
    }

    @Override
    public String description() {
        return description;
    }
}
