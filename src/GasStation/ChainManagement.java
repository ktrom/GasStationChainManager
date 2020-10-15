package GasStation;

import DatabaseClasses.DatabaseSupport;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class ChainManagement {

    /**
     * Deploy gas station management fleet to restock specified gas station.
     *
     * @param inventory inventory to be restocked
     * @return true if fleet deployed successfully, false otherwise
     */
    public boolean deployFleet(Inventory[] inventory) throws SQLException {

        // Process each item
        for(Inventory inv : inventory) {

            // Validate the inventory item
            if (DatabaseSupport.getInventoryItem(inv.getGasStationID(), inv.getItemID()) == null) {
                throw new NoSuchElementException("Invalid item " + inv.getItemID() + ".");
            }

            // Update the inventory
            Inventory invUpdated = new Inventory(inv.getGasStationID(), inv.getItemID());

            // Pull down the existing inventory item for this gas station
            if (invUpdated.pull()) {
                // The inventory
            } else {
                // Gas station doesn't offer the item, return false
                return false;
            }
        }

        return false;
    }
}
