package GasStation;

import DatabaseClasses.DatabaseSupport;

import java.sql.SQLException;

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
                // Gas station doesn't sell this item
                return false;
            }

            // Update the inventory
            Inventory invUpdated = new Inventory(inv.getGasStationID(), inv.getItemID());
            invUpdated.pull();

            // Update the stock
            invUpdated.setQuantity(invUpdated.getQuantity() + inv.getQuantity());

            // Push updated quantities in stock
            invUpdated.push();
        }

        // Reached this point, all items have been processed
        return true;
    }
}
