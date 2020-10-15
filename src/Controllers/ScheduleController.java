package Controllers;

import GasStation.Employee;
import GasStation.GasStation;
import GasStation.Schedule;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class ScheduleController {

    /**
     * schedules an employee at the given manager's gas station
     * @param managerId
     */
     public void scheduleEmployee(int managerId) {
         Scanner s = new Scanner(System.in);

         Employee manager = new Employee(managerId);

         try {
             manager.pull();
         } catch (SQLException throwables) {
             System.out.println("Error pulling manager");
             throwables.printStackTrace();
         }

         GasStation currentStation = new GasStation(manager.getGasStationID());

         ArrayList<Employee> availableEmployees = new ArrayList<Employee>();
         try {
            availableEmployees = currentStation.getEmployees();
         } catch (SQLException throwables) {
             System.out.println("Error finding available employees");
             throwables.printStackTrace();
         }

         System.out.println("Which Employee would you like to schedule?");
         Iterator<Employee> i = availableEmployees.iterator();
         int j = 1;
         while(i.hasNext()){
             Employee nextEmployee = i.next();
             System.out.println(j + ": " + nextEmployee.getName());
             j++;
         }
         int selectedIndex = s.nextInt() - 1;
         Employee selectedEmployee = availableEmployees.get(selectedIndex);

         int employeeId = selectedEmployee.getEmployeeID();

         String date = "";

         System.out.println("What day would you like to schedule the employee for? (MM/DD/YYYY)\n");
         System.out.println("MM: ");
         date += s.next();

         System.out.println("\nDD: ");
         date += s.next();

         System.out.println("YYYY: ");
         date += s.next();

         Date d = Date.valueOf(date);

         System.out.println("What shift would you like to schedule the employee for?");
         System.out.println("1: 7-3");
         System.out.println("2: 3-11");
         System.out.println("3: 11-7");

         System.out.println("Enter 1, 2, or 3: ");
         int shift = s.nextInt();

         Schedule schedule = new Schedule(managerId, employeeId, d, shift);
         try {
             schedule.create();
         } catch (SQLException throwables) {
             System.out.println("Schedule addition failed.");
             throwables.printStackTrace();
         }
     }

}
