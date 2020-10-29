package Commands;

import Controllers.EmployeeController;
import Controllers.GasStationController;
import Controllers.ScheduleController;
import GasStation.Employee;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ScheduleEmployeeCommand implements Command {
    private final static String description = "Schedule Employee";
    private int managerID;

    public ScheduleEmployeeCommand(int managerID){
        this.managerID = managerID;
    }
    /**
     * schedules an employee at the given manager's gas station
     *
     */
    private void scheduleEmployee() {
        Scanner s = new Scanner(System.in);

        EmployeeController ec = new EmployeeController();
        int gasStationID = ec.getGasStationID(managerID);

        GasStationController gsc = new GasStationController();
        ArrayList<Employee> availableEmployees = gsc.getEmployees(gasStationID);

        if (availableEmployees.size() == 0) {
            System.out.print("No employees in system!");
        } else {
            System.out.println("Which Employee would you like to schedule?");
            Iterator<Employee> i = availableEmployees.iterator();
            int j = 1;
            while (i.hasNext()) {
                Employee nextEmployee = i.next();
                System.out.println(j + ": " + nextEmployee.getName());
                j++;
            }
            int selectedIndex = s.nextInt() - 1;

            Employee selectedEmployee = availableEmployees.get(selectedIndex);

            int employeeId = selectedEmployee.getEmployeeID();

            String date = "";

            System.out.println("What day would you like to schedule the employee for? (YYYY-MM-DD)");
            date = s.next();

            Date d = Date.valueOf(date);

            System.out.println("What shift would you like to schedule the employee for?");
            System.out.println("1: 7-3");
            System.out.println("2: 3-11");
            System.out.println("3: 11-7");

            System.out.println("Enter 1, 2, or 3: ");
            int shift = s.nextInt();

            ScheduleController sc = new ScheduleController();
            sc.scheduleEmployee(gasStationID, employeeId, d, shift);

            PrintScheduleCommand printSched = new PrintScheduleCommand(managerID);
            printSched.execute();
        }
    }

    @Override
    public void execute() {
        scheduleEmployee();
    }

    @Override
    public String description() {
        return description;
    }
}