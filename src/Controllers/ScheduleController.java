package Controllers;

import GasStation.Employee;
import GasStation.GasStation;
import GasStation.Schedule;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleController{

    public ArrayList<Employee> getEmployees(int gasStationID)  {
        GasStation g = new GasStation(gasStationID);
        g.pull();

        try {
            return g.getEmployees();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean createSchedule(int gasStationID, int employeeId, Date date, int shift){
        Schedule schedule = new Schedule(gasStationID, employeeId, date, shift);
        schedule.create();
        return true;
    }

}