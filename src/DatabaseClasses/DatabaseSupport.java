package DatabaseClasses;

import GasStation.Inventory;
import GasStation.Item;

import java.sql.SQLException;

public class DatabaseSupport {

    /**
     * Get an item at a given gas station.
     *
     * @param GasStationID given gas station
     * @param ItemID given item id
     * @return the Item if found, null otherwise
     */
    public static Item getInventoryItem(int GasStationID, int ItemID) throws SQLException {
        // Get the inventory entry for item at the given gas station
        Inventory inv = new Inventory(GasStationID, ItemID);

        // Item is sold by the given gas station
        if (inv.pull()) {
            // Get the gas station item
            Item item = new Item(inv.getItemID());
            item.pull();

            // Return found item
            return item;
        }

        // Item not found
        return null;
    }
}
