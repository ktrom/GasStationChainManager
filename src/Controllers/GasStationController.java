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
     * Crates and adds row to the GasStation table
     * @param Location The location of the Gas Station
     */
    public void createGasStation(String Location){
        GasStation g = new GasStation(Location);
        g.create();
    }

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
}
