package Controllers;

import GasStation.Schedule;

import java.sql.Date;
import java.sql.SQLException;

public class ScheduleController {
     public void scheduleEmployee(int managerId, int employeeId, Date date, int shift) {
         Schedule s = new Schedule(managerId, employeeId, date, shift);
         try {
             s.create();
         } catch (SQLException throwables) {
             System.out.println("Schedule addition failed.");
             throwables.printStackTrace();
         }
     }

}
