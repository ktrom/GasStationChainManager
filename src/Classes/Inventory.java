package Classes;

public class Inventory {
    private String inventoryID;
    private String gasStationID;
    private String itemID;
    private double quantity;

    public String getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(String inventoryID) {
        this.inventoryID = inventoryID;
    }

    public String getGasStationID() {
        return gasStationID;
    }

    public void setGasStationID(String gasStationID) {
        this.gasStationID = gasStationID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Inventory(String inventoryID, String gasStationID, String itemID, double quantity) {
        this.inventoryID = inventoryID;
        this.gasStationID = gasStationID;
        this.itemID = itemID;
        this.quantity = quantity;
    }
}
