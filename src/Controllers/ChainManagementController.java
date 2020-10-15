package Controllers;

import GasStation.*;

import java.sql.SQLException;

public class ChainManagementController {

    /**
     * Chain management instance.
     */
    private ChainManagement management = null;

    /**
     * Deploy gas station management fleet to restock specified gas station.
     *
     * @param inventory items and quantities to restock
     * @return true if fleet deployed successfully, false otherwise
     */
    public boolean deployFleet(Inventory inventory) throws SQLException {
        return this.getChainManagementInstance().deployFleet(inventory);
    }

    /**
     * Get an instance of a chain management class.
     *
     * @return chain management instance
     */
    private ChainManagement getChainManagementInstance() {
        this.management = new ChainManagement();
        return this.management;
    }
}
