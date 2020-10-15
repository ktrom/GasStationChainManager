package GasStation;

import DatabaseClasses.DatabaseSupport;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Sale {

    /**
     * Sell specified quantity of an item.
     *
     * @param GasStationID gas station to sell from
     * @param ItemID item to sell
     * @param Quantity quantity to sell
     * @return number of items sold
     */
    public int sellItem(int GasStationID, int ItemID, int Quantity) throws SQLException {
        // Confirm item is valid
        if (!DatabaseSupport.isValidItem(ItemID)) {
            // Invalid item
            throw new NoSuchElementException("Invalid item: " + ItemID + ".");
        }

        // Confirm gas station sells the item
        if (!DatabaseSupport.sellsItem(GasStationID, ItemID)) {
            // Gas station doesn't sell the item
            throw new IllegalArgumentException("Gas station " + GasStationID + " does not sell item " + ItemID + ".");
        }

        // Get inventory
        Inventory inv = new Inventory(GasStationID, ItemID);
        inv.pull();

        // Get possible quantity to sell
        int quantityToSell = Math.min(Quantity, inv.getQuantity());

        // Calculate sale total
        Double saleTotal = DatabaseSupport.calculateSaleTotal(ItemID, quantityToSell);

        // Update inventory
        DatabaseSupport.removeInventory(GasStationID, ItemID, quantityToSell);

        return quantityToSell;
    }
}
