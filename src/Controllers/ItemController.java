package Controllers;

import GasStation.Item;

/**
 * Controller for handling any requests with Items
 */
public class ItemController {

    /**
     * Creates and adds an Item to the database
     * @param name Item name
     * @param price Item price
     * @param supplierPrice Item purchase price
     * @param photoURL Item picture url
     * @param notes Notes about the item
     * @return true if successful creation & save of item, false otherwise
     */
    public boolean createItem(String name, double price, double supplierPrice, String photoURL, String notes){
        Item i = new Item(name, price, supplierPrice, photoURL, notes);
        return i.create();
    }
}
