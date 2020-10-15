package Controllers;

import GasStation.*;

import java.sql.SQLException;

public class SaleController {

    /**
     * Sale instance.
     */
    private Sale sale = null;

    /**
     * Sell specified quantity of an item.
     *
     * @param GasStationID gas station to sell from
     * @param ItemID item to sell
     * @param Quantity quantity to sell
     * @return number of items sold
     */
    public int sellItem(int GasStationID, int ItemID, int Quantity) throws SQLException {
        return this.getSaleInstance().sellItem(GasStationID, ItemID, Quantity);
    }

    /**
     * Get an instance of a chain management class.
     *
     * @return chain management instance
     */
    private Sale getSaleInstance() {
        this.sale = new Sale();
        return this.sale;
    }
}
