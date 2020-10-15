package Controllers;

import GasStation.Item;

/**
 * Controller for handling any requests with Items
 */
public class ItemController {

    public boolean createItem(String name, double price, double supplierPrice, String photoURL, String notes){
        Item i = new Item(name, price, supplierPrice, photoURL, notes);
        return i.create();
    }
}
