package Controllers;


import DatabaseClasses.DatabaseSupport;
import GasStation.GasStation;
import GasStation.Schedule;
import HelperClasses.HelperFunctions;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles database queries for the Schedule class
 */
public class ScheduleController{

    /**
     * Returns a string of the gas station schedule in the format
     * <Employee name, Scheduled Date, Scheduled Shift> ordered by date, then shift
     * for all scheduled persons at the gas station.
     * @param gasStationId The gas station to get the schedule for
     * @return A string representation of the schedule
     */
    public String gasStationSchedule(int gasStationId){
        GasStation g = new GasStation(gasStationId);
        g.pull();

        try {
            return DatabaseSupport.gasStationScheduleString(g.getGasStationID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Adds an employee to the schedule by creating a Schedule object and saving it to the database
     * @param gasStationID ID of the gas station
     * @param employeeId ID of the employee
     * @param date Date to schedule employee for
     * @param shift Shift to schedule employee for
     * @return true if successful creation & save, false otherwise
     */
    public boolean
    scheduleEmployee(int gasStationID, int employeeId, Date date, int shift){
        Schedule schedule = new Schedule(gasStationID, employeeId, date, shift);
        schedule.create();
        return true;
    }

}