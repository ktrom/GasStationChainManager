package Controllers;

import GasStation.GasStation;

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
}
