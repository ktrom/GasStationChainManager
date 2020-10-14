package Controllers;

import GasStation.*;
import java.util.HashMap;

public class ChainManagementController {

    /**
     * Chain management instance.
     */
    private ChainManagement management = null;

    /**
     * Deploy gas station management fleet to restock specified gas station.
     *
     * @param sid gas station to restock
     * @param items map of items and the number to restock of each
     * @return true if fleet deployed successfully, false otherwise
     */
    public boolean deployFleet(int sid, HashMap<Item, Integer> items) {
        return this.getChainManagementInstance().deployFleet(sid, items);
    }

    /**
     * Get an instance of a chain management class.
     *
     * @return chain management instance
     */
    public ChainManagement getChainManagementInstance() {
        this.management = new ChainManagement();
        return this.management;
    }
}
