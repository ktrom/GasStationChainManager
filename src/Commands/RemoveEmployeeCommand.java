package Commands;

import Controllers.HiringManagerController;
import GasStation.Employee;

import java.sql.SQLException;
import java.util.Scanner;

public class RemoveEmployeeCommand implements Command{
    private static final String description = "Remove Employee";
    private int actingEmployeeID;
    public RemoveEmployeeCommand(int actingEmployeeID){
        this.actingEmployeeID = actingEmployeeID;
    }
    /**
     * Operation that takes an employee to remove from the database
     * @return
     * @throws SQLException
     */
    private boolean removeEmployee() {
        Scanner s = new Scanner(System.in);
        System.out.println("\n Enter Employee ID you wish to remove: ");
        int employeeID = s.nextInt();
        HiringManagerController hmc = new HiringManagerController();
        Employee e = new Employee(employeeID);
        e.pull();
        while(true){
            System.out.println("Would you like to remove " + e.getName() + " at gas station " + e.getGasStationID() + "? ('y' or 'n'): ");
            String yOrN = s.next();
            if(yOrN.equals("y")){
                hmc.deleteEmployee(employeeID);
                System.out.println(e.getName() + " has been removed.");
                return true;
            } else if (yOrN.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid Answer");
            }
        }

    }

    @Override
    public void execute() {
        removeEmployee();
    }

    @Override
    public String description() {
        return description;
    }
}
