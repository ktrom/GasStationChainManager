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

    /**
     * Add an item to be sold at a gas station.
     *
     * @param GasStationID given gas station
     * @param ItemID item to begin sale of
     * @return Item created
     */
    public static void addInventoryItem(int GasStationID, int ItemID) throws SQLException {
        // Create new inventory entry
        Inventory inv = new Inventory(GasStationID, ItemID, 0);
        inv.create();
    }

    /**
     * Calculate the total sale price.
     *
     * @param ItemID item to sell
     * @param Quantity quantity of item to sell
     * @return total sale price
     */
    public static Double calculateSaleTotal(int ItemID, int Quantity) throws SQLException {
        // Get the given item
        Item item = new Item(ItemID);
        item.pull();

        // Return total
        return item.getPrice() * Quantity;
    }

    /**
     * Determine if an item exists.
     *
     * @param ItemID given item
     * @return true if item is valid, false otherwise
     */
    public static boolean isValidItem(int ItemID) throws SQLException {
        Item item = new Item(ItemID);
        return item.pull();
    }

    /**
     * Determine if a gas station sells an item.
     *
     * @param GasStationID gas station to check
     * @param ItemID item to check
     * @return true if the gas station sells the item, false otherwise
     */
    public static boolean sellsItem(int GasStationID, int ItemID) throws SQLException {
        Inventory inv = new Inventory(GasStationID, ItemID);
        return inv.pull();
    }
}
