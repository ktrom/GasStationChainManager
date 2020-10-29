package Controllers;

import DatabaseClasses.DatabaseSupport;
import GasStation.GasStation;

import java.sql.SQLException;
import java.util.ArrayList;
import GasStation.Employee;

/**
 * Controller for handling GasStation-related requests
 */
public class GasStationController {

    /**
     * Returns a lit of all employees at the given gas station
     * @param gasStationID Desired GasStation
     * @return ArrayList of all Employees at given GasStation
     */
    public ArrayList<Employee> getEmployees(int gasStationID)  {
        GasStation g = new GasStation(gasStationID);
        g.pull();

        try {
            return DatabaseSupport.getStationEmployees(g.getGasStationID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Returns location of gas station
     * @param gasStationID ID of gas station
     * @return String with location
     */
    public String getLocation(int gasStationID){
        GasStation g = new GasStation(gasStationID);
        g.pull();
        return g.getLocation();
    }

    /**
     * Assigns random tasks to employees in this gas station
     * @param gasStationID Gas Station ID
     * @param desscriptions Descriptions of tasks
     * @return true if successful, false otherwise
     */
    public boolean assignRandomTasks(int gasStationID, ArrayList<String> desscriptions){
        GasStation g = new GasStation(gasStationID);
        g.pull();
        g.pull();
        g.assignRandomTasks(desscriptions);
        return true;
    }
}
