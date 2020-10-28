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
    public boolean deployFleet(Inventory inventory) throws SQLException {
        // Validate the inventory item
        if (DatabaseSupport.getInventoryItem(inventory.getGasStationID(), inventory.getItemID()) == null) {
            // Gas station doesn't sell this item
            return false;
        }

        // Update the inventory
        Inventory invUpdated = new Inventory(inventory.getGasStationID(), inventory.getItemID());
        invUpdated.pull();

        // Update the stock
        invUpdated.setQuantity(invUpdated.getQuantity() + inventory.getQuantity());
        invUpdated.push();

        return true;
    }

    /**
     * Construct a new gas station with the given values.
     *
     * @param station GasStation instance
     * @return true if gas station valid, false otherwise
     */
    public boolean buildGasStation(GasStation station) throws SQLException {
        // Determine if the location is valid
        if (!DatabaseSupport.isValidStationLocation(station.getLocation())) {
            throw new IllegalArgumentException("location");
        }

        // Determine if the phone number is valid
        if (!DatabaseSupport.isValidPhoneNumber(station.getPhoneNumber())) {
            throw new IllegalArgumentException("phone");
        }

        // Log the transaction


        // Create the new gas station record
        return station.create();
    }
}
