package Controllers;

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
}
