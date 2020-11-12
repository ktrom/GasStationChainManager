package Commands;

import GasStation.Employee;
import GasStation.EmployeePosition;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandExecutor {
    private int employeeID;
    public CommandExecutor(int employeeID){
        this.employeeID = employeeID;
    }

    private ArrayList<Command> getEmployeeCommands(){
        Employee employee = new Employee(employeeID);
        employee.pull();

        ArrayList<Command> employeeCommands = new ArrayList<Command>();
        switch (employee.getEmployeePosition()){
            case ATTENDANT:
                employeeCommands.add(new MakeTransactionCommand(employeeID));
                employeeCommands.add(new SellGasCommand((employeeID)));
                employeeCommands.add(new ViewOrCompleteTaskCommand(employeeID));
                employeeCommands.add(new CheckLotteryCommand(employeeID));
                break;
            case MANAGER:
                employeeCommands.add(new ScheduleEmployeeCommand(employeeID));
                employeeCommands.add(new AssignTaskCommand(employeeID));
                employeeCommands.add(new RandomlyAssignTasksCommand(employeeID));
                employeeCommands.add(new PrintScheduleCommand(employeeID));
                employeeCommands.add(new PrintTasksCommand(employeeID));
                break;
            case CFO:
                employeeCommands.add(new ViewFinancialsCommand());
                break;
            case COO:
                employeeCommands.add(new BuildGasStationCommand(employeeID));
                employeeCommands.add(new ManageGasStationCommand(employeeID));
                employeeCommands.add(new DeployFleetCommand(employeeID));
                employeeCommands.add(new AddItemCommand(employeeID));
                break;
            case HIRING_MANAGER:
                employeeCommands.add(new HireEmployeeCommand(employeeID));
                employeeCommands.add(new UpdateEmployeeCommand(employeeID));
                employeeCommands.add(new RemoveEmployeeCommand(employeeID));
                break;
        }

        return employeeCommands;
    }

    public void executeCommands(){
        int option = 0;
        Scanner scan = new Scanner(System.in);

        ArrayList<Command> menuCommands = getEmployeeCommands();

        while (option >= 0 && option < menuCommands.size()) {

            System.out.println("Select an option or enter -1 to quit:");
            for(int i = 0; i < menuCommands.size(); i++){
                System.out.println((i + 1) + ". " + menuCommands.get(i).description());
            }
            option = scan.nextInt() - 1;

            if(option >= 0 && option < menuCommands.size()){
                menuCommands.get(option).execute();
            }
        }
    }
}
